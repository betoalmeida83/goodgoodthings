package com.goodgoodthings.services;

import com.goodgoodthings.dtos.AlbumDTO;
import com.goodgoodthings.dtos.ImagemDTO;
import com.goodgoodthings.entities.Album;
import com.goodgoodthings.entities.Imagem;
import com.goodgoodthings.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public AlbumDTO toDTO(Album album) {
        List<ImagemDTO> imagensDTO = album.getImagens().stream()
                .map(img -> ImagemDTO.builder()
                        .id(img.getId())
                        .url(img.getUrl())
                        .tipo(img.getTipo())
                        .build())
                .collect(Collectors.toList());

        return AlbumDTO.builder()
                .id(album.getId())
                .albumTitulo(album.getAlbumTitulo())
                .artista(album.getArtista())
                .gravadora(album.getGravadora())
                .anoLancamento(album.getAnoLancamento())
                .genero(album.getGenero())
                .descricao(album.getDescricao())
                .formato(album.getFormato())
                .preco(album.getPreco())
                .imagens(imagensDTO)
                .build();
    }

    public Album fromDTO(AlbumDTO dto) {
        Album album = new Album();
        album.setAlbumTitulo(dto.getAlbumTitulo());
        album.setArtista(dto.getArtista());
        album.setGravadora(dto.getGravadora());
        album.setAnoLancamento(dto.getAnoLancamento());
        album.setGenero(dto.getGenero());
        album.setDescricao(dto.getDescricao());
        album.setFormato(dto.getFormato());
        album.setPreco(dto.getPreco());

        if (dto.getImagens() != null) {
            List<Imagem> imagens = dto.getImagens().stream().map(imgDTO -> {
                Imagem img = new Imagem();
                img.setUrl(imgDTO.getUrl());
                img.setTipo(imgDTO.getTipo());
                img.setAlbum(album);
                return img;
            }).toList();
            album.setImagens(imagens);
        }

        return album;
    }

    // Criar novo Album
    public AlbumDTO salvarAlbum(AlbumDTO dto) {
        Album album = fromDTO(dto);
        return toDTO(albumRepository.save(album));
    }

    // Buscar todos os Álbuns
    public List<AlbumDTO> buscarTodosAlbuns() {
        return albumRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar Álbum por Id
    public AlbumDTO buscarAlbumPorId(Long id) {
        return albumRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Album não encontrado!"));
    }

    // Buscar Álbuns por Artista
    public List<AlbumDTO> buscarAlbunsPorArtista(String artista) {
        return albumRepository.findByArtistaContainingIgnoreCase(artista)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar Álbum por Título
    public List<AlbumDTO> buscarAlbumPorTitulo(String titulo) {
        return albumRepository.findByAlbumTituloContainingIgnoreCase(titulo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar Álbuns por Genêro
    public List<AlbumDTO> buscarAlbunsPorGenero(String genero) {
        return albumRepository.findByGeneroContainingIgnoreCase(genero)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar Álbuns por Formato da Mídia
    public List<AlbumDTO> buscarAlbunsPorFormato(String formato) {
        return albumRepository.findByFormatoContainingIgnoreCase(formato)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Atualizar Album
    public AlbumDTO atualizarAlbum(Long id, AlbumDTO albumDTO) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum não encontrado!"));

        album.setAlbumTitulo(albumDTO.getAlbumTitulo());
        album.setArtista(albumDTO.getArtista());
        album.setGravadora(albumDTO.getGravadora());
        album.setAnoLancamento(albumDTO.getAnoLancamento());
        album.setGenero(albumDTO.getGenero());
        album.setDescricao(albumDTO.getDescricao());
        album.setFormato(albumDTO.getFormato());
        album.setPreco(albumDTO.getPreco());

        if (albumDTO.getImagens() != null) {
            album.getImagens().clear();

            List<Imagem> novasImagens = albumDTO.getImagens().stream().map(imgDTO -> {
                Imagem img = new Imagem();
                img.setUrl(imgDTO.getUrl());
                img.setTipo(imgDTO.getTipo());
                img.setAlbum(album);
                return img;
            }).toList();

            album.getImagens().addAll(novasImagens);
        }

        return toDTO(albumRepository.save(album));
    }

    // Deletar Album
    public boolean deletarAlbum(Long id) {
        if (!albumRepository.existsById(id)) {
            return false;
        }
        albumRepository.deleteById(id);
        return true;
    }

}
