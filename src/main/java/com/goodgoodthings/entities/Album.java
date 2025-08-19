package com.goodgoodthings.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albuns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "album_titulo", nullable = false)
    private String albumTitulo;

    @Column(nullable = false)
    private String artista;
    private String gravadora;

    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

    private String genero;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Formato formato;

    @Column(nullable = false)
    private Double preco;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagens = new ArrayList<>();

}
