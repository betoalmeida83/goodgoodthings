package com.goodgoodthings.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoImagem tipoImagem;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Album album;

}
