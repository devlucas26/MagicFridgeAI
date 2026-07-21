package dev.java10x.MagicFridgeAI.enums;

public enum Categoria {
    LATICINIOS("Laticínios"),
    VEGETAIS("Vegetais"),
    CARNES("Carnes"),
    FRUTAS("Frutas"),
    BEBIDAS("Bebidas"),
    OUTROS("Outros");
    private final String descricao;
    Categoria(String descricao){
        this.descricao = descricao;
    }
}
