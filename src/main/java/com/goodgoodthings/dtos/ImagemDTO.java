package com.goodgoodthings.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagemDTO {

    private Long id;
    private String url;
    private String tipo;

}
