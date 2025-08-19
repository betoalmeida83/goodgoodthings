package com.goodgoodthings.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Album album;

}
