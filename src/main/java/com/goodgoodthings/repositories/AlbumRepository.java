package com.goodgoodthings.repositories;

import com.goodgoodthings.entities.Album;
import com.goodgoodthings.entities.Formato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

}
