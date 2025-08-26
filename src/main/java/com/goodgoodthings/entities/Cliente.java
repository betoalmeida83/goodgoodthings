package com.goodgoodthings.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_cliente", unique = true, nullable = false)
    private String codigoCliente;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Gênero é obrigatório")
    @Enumerated(EnumType.STRING)
    private GeneroCliente generoCliente;

    @NotNull(message = "Data de Nascimento é obrigatória")
    @Column(name = "data_nascimento", unique = true, nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos")
    @Column(unique = true, nullable = false)
    private String cpf;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String senha;

    private Integer ranking = 0;

    @Builder.Default
    private Boolean ativo = true;

}
