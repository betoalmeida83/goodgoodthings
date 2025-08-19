package com.goodgoodthings.dtos;

import com.goodgoodthings.entities.Formato;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumDTO {

    private Long id;
    private String albumTitulo;
    private String artista;
    private String gravadora;
    private Integer anoLancamento;
    private String genero;
    private String descricao;
    private Formato formato;
    private Double preco;

    private List<ImagemDTO> imagens;

}
