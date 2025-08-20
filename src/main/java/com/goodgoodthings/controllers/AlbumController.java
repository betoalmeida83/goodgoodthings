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

    // Buscar Álbum por Id
    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> buscarAlbumPorId(@PathVariable Long id) {
        AlbumDTO bucarAlbumID = albumService.buscarAlbumPorId(id);
        return ResponseEntity.ok(bucarAlbumID);
    }

    // Buscar Álbuns por Artista
    @GetMapping("/buscar/por-artista")
    public ResponseEntity<List<AlbumDTO>> buscarAlbunsPorArtista(@RequestParam String artista) {
        List<AlbumDTO> buscarAlbunsPorArtista = albumService.buscarAlbunsPorArtista(artista);
        return buscarAlbunsPorArtista.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(buscarAlbunsPorArtista);
    }

    // Buscar Álbum por Título
    @GetMapping("/buscar/por-titulo")
    public ResponseEntity<List<AlbumDTO>> buscarAlbumPorTitulo(@RequestParam String titulo) {
        List<AlbumDTO> buscarAlbumPorTitulo = albumService.buscarAlbumPorTitulo(titulo);
        return buscarAlbumPorTitulo.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(buscarAlbumPorTitulo);
    }

    // Buscar Álbum por Genêro
    @GetMapping("/buscar/por-genero")
    public ResponseEntity<List<AlbumDTO>> buscarAlbunsPorGenero(@RequestParam String genero) {
        List<AlbumDTO> buscarAlbumPorGenero = albumService.buscarAlbunsPorGenero(genero);
        return buscarAlbumPorGenero.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(buscarAlbumPorGenero);
    }

    // Buscar Álbum por Formato da Mídia
    @GetMapping("/buscar/por-formato")
    public ResponseEntity<List<AlbumDTO>> buscarAlbunsPorFormato(@RequestParam String formato) {
        List<AlbumDTO> buscarAlbunsPorFormato = albumService.buscarAlbunsPorFormato(formato);
        return buscarAlbunsPorFormato.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(buscarAlbunsPorFormato);
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
