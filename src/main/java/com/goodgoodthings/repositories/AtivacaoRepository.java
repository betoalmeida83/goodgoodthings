package com.goodgoodthings.repositories;

import com.goodgoodthings.entities.Ativacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtivacaoRepository  extends JpaRepository<Ativacao, Long> {

    Optional<Ativacao> findByMotivoAtivacao(String motivoAtivacao);

}
