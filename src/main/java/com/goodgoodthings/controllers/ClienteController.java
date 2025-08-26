package com.goodgoodthings.controllers;

import com.goodgoodthings.dtos.AlterarSenhaDTO;
import com.goodgoodthings.dtos.ClienteDTO;
import com.goodgoodthings.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> salvarCliente(@Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(clienteService.salvarCliente(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarClientePorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscarTodosClientes() {
        return ResponseEntity.ok(clienteService.buscarTodosClientes());
    }

    // Buscar Clientes com base em um filtro definido
    @GetMapping("/filtro")
    public ResponseEntity<List<ClienteDTO>> filtrarClientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) Boolean ativo) {
        return ResponseEntity.ok(clienteService.filtrarClientes(nome, email, cpf, ativo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(clienteService.atualizarCliente(id, dto));

    }

    // Atualizar Senha do Cliente
    @PatchMapping("/{id}/senha")
    public ResponseEntity<ClienteDTO> atualizarSenhaCliente(@PathVariable Long id, @Valid @RequestBody AlterarSenhaDTO dto) {
        return ResponseEntity.ok(clienteService.atualizarSenhaCliente(id, dto));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativarCliente(@PathVariable Long id) {
        clienteService.inativarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
