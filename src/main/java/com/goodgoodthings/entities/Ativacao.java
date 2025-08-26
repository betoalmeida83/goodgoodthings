package com.goodgoodthings.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ativacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ativacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String motivoAtivacao;

    @OneToMany(mappedBy = "motivoAtivacao")
    private List<Album> albuns = new ArrayList<>();
}

