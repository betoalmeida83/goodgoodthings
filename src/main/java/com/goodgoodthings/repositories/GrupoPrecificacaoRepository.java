package com.goodgoodthings.repositories;

import com.goodgoodthings.entities.GrupoPrecificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrupoPrecificacaoRepository extends JpaRepository<GrupoPrecificacao, Long> {

    Optional<GrupoPrecificacao> findByNome(String nome);

}
