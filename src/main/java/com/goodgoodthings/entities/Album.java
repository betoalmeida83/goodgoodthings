package com.goodgoodthings.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albuns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "album_titulo", nullable = false)
    private String albumTitulo;

    @Column(nullable = false)
    private String artista;

    @Column(nullable = false)
    private String gravadora;

    @Column(name = "ano_lancamento", nullable = false)
    private Integer anoLancamento;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Formato formato;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private Double precoCusto;

    @Column(nullable = false)
    private Integer estoque = 0;

    @Column(nullable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagens = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "album_generos", joinColumns = @JoinColumn(name = "album_id"), inverseJoinColumns = @JoinColumn(name = "genero_id"))
    private List<Genero> generos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "grupo_precificacao_id", nullable = false)
    private GrupoPrecificacao grupoPrecificacao;

    @ManyToOne
    @JoinColumn(name = "motivo_inativacao_id")
    private Inativacao motivoInativacao;

    @ManyToOne
    @JoinColumn(name = "motivo_ativacao_id")
    private Ativacao motivoAtivacao;

}
