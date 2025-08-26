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
    private String descricao;
    private Formato formato;
    private Double preco;
    private Double precoCusto;

    private Integer estoque;
    private boolean ativo;

    private List<String> generos;

    private String grupoPrecificacao;

    private List<ImagemDTO> imagens;

    private String motivoInativacao;
    private String motivoAtivacao;

}
