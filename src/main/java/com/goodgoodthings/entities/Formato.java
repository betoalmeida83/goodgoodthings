package com.goodgoodthings.entities;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum Formato {

    CD("CD"),
    VINIL_12("Vinil 12\""),
    VINIL_10("Vinil 10\""),
    VINIL_17("Vinil 7\""),
    K7("Fita K7"),
    DVD("DVD"),
    BLUE_RAY("Blue Ray");

    private String descricao;

    Formato(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Formato fromDescricao(String descricao) {
        for (Formato formato : Formato.values()) {
            if (formato.getDescricao().equalsIgnoreCase(descricao)) {
                return formato;
            }
        }
        throw new IllegalArgumentException("Formato inválido: " + descricao);
    }

}
