package com.goodgoodthings.services;

import com.goodgoodthings.dtos.AlbumDTO;
import com.goodgoodthings.dtos.ImagemDTO;
import com.goodgoodthings.entities.Album;
import com.goodgoodthings.entities.Imagem;
import com.goodgoodthings.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
