package com.goodgoodthings.repositories;

import com.goodgoodthings.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

    @Query("SELECT c FROM Cliente c " +
            "WHERE (:nome IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
            "AND (:email IS NULL OR LOWER(c.email) = LOWER(:email)) " +
            "AND (:cpf IS NULL OR c.cpf = :cpf) " +
            "AND (:ativo IS NULL OR c.ativo = :ativo)")
    List<Cliente> filtrarClientes(@Param("nome") String nome,
                                  @Param("email") String email,
                                  @Param("cpf") String cpf,
                                  @Param("ativo") Boolean ativo);

}
