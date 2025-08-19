package com.goodgoodthings.controllers;

import com.goodgoodthings.dtos.AlbumDTO;
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

}
