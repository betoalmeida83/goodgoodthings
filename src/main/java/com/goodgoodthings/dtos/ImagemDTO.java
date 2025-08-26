package com.goodgoodthings.dtos;

import com.goodgoodthings.entities.TipoImagem;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagemDTO {

    private Long id;
    private String url;

    private TipoImagem tipo;

}
