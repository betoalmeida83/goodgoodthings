package com.goodgoodthings.repositories;

import com.goodgoodthings.entities.Inativacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InativacaoRepository extends JpaRepository<Inativacao, Long> {

    Optional<Inativacao> findByMotivoInativacao(String motivoInativacao);

}
