package com.goodgoodthings.services;

import com.goodgoodthings.dtos.AlterarSenhaDTO;
import com.goodgoodthings.dtos.ClienteDTO;
import com.goodgoodthings.entities.Cliente;
import com.goodgoodthings.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private ClienteDTO toDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .codigoCliente(cliente.getCodigoCliente())
                .nome(cliente.getNome())
                .generoCliente(cliente.getGeneroCliente())
                .dataNascimento(cliente.getDataNascimento())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .ranking(cliente.getRanking())
                .ativo(cliente.getAtivo())
                .build();
    }

    // Cadastrar novo Cliente
    public ClienteDTO salvarCliente(ClienteDTO dto) {

        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("CPF já cadastrado!");
        }
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        Cliente cliente = Cliente.builder()
                .codigoCliente("CLI-" + UUID.randomUUID().toString().substring(0,8).toUpperCase())
                .nome(dto.getNome())
                .generoCliente(dto.getGeneroCliente())
                .dataNascimento(dto.getDataNascimento())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .ranking(0)
                .ativo(true)
                .build();

        Cliente salvo = clienteRepository.save(cliente);
        return toDTO(salvo);

    }

    // Buscar Cliente por Id
    public ClienteDTO buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    // Buscar todos os Clientes
    public List<ClienteDTO> buscarTodosClientes() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    // Buscar Clientes com base em um filtro definido
    public List<ClienteDTO> filtrarClientes(String nome, String email, String cpf, Boolean ativo) {
        return clienteRepository.filtrarClientes(nome, email, cpf, ativo)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Atualizar dados do cliente
    public ClienteDTO atualizarCliente(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setNome(dto.getNome());
        cliente.setGeneroCliente(dto.getGeneroCliente());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(dto.getSenha());

        return toDTO(clienteRepository.save(cliente));
    }

    // Atualizar Senha do Cliente
    public ClienteDTO atualizarSenhaCliente(Long id, AlterarSenhaDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setSenha(dto.getNovaSenha());
        return toDTO(clienteRepository.save(cliente));
    }

    public void inativarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }


}
