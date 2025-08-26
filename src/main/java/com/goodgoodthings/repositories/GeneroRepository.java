package com.goodgoodthings.repositories;

import com.goodgoodthings.entities.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    List<Genero> findAllByNomeIn(List<String> nomes);

    Optional<Genero> findByNome(String nome);

}
