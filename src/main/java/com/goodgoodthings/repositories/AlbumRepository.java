package com.goodgoodthings.repositories;

import com.goodgoodthings.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByArtista(String artista);

    List<Album> findByGenero(String genero);

    List<Album> findByAlbumTituloContainingIgnoreCase(String titulo);

    List<Album> findByFormato(String formato);

}
