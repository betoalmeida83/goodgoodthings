package com.goodgoodthings.controllers;

import com.goodgoodthings.dtos.AlbumDTO;
import com.goodgoodthings.entities.Formato;
import com.goodgoodthings.services.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albuns")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    // Criar novo Álbum
    @PostMapping
    public ResponseEntity<AlbumDTO> salvarAlbum(@Valid @RequestBody AlbumDTO dto) {
        AlbumDTO salvar = albumService.salvarAlbum(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvar);
    }

    // Buscar todos os Álbuns
    @GetMapping
    public ResponseEntity<List<AlbumDTO>> buscarTodosAlbuns() {
        List<AlbumDTO> buscarAlbuns = albumService.buscarTodosAlbuns();
        return buscarAlbuns.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(buscarAlbuns);
    }

    // Buscar Álbum por Id
    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> buscarAlbumPorId(@PathVariable Long id) {
        AlbumDTO bucarAlbumID = albumService.buscarAlbumPorId(id);
        return ResponseEntity.ok(bucarAlbumID);
    }

    @GetMapping("/albuns/filtro")
    public ResponseEntity<List<AlbumDTO>> buscarAlbunsFiltrados(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String artista,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Formato formato) {

        List<AlbumDTO> albuns = albumService.buscarAlbunsFiltrados(titulo, artista, genero, formato);
        return ResponseEntity.ok(albuns);
    }

    // Atualizar Album
    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> atualizarAlbum(@PathVariable Long id, @Valid @RequestBody AlbumDTO albumDTO) {
        AlbumDTO atualizar = albumService.atualizarAlbum(id, albumDTO);
        return ResponseEntity.ok(atualizar);
    }

    // Deletar Album
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAlbum(@PathVariable Long id) {
        boolean deletar = albumService.deletarAlbum(id);
        return deletar
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
