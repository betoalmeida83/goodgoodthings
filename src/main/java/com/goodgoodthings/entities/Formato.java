package com.goodgoodthings.entities;

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

    public String getDescricao() {
        return descricao;
    }

}
