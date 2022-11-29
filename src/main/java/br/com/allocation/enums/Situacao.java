package br.com.allocation.enums;

public enum Situacao {
    ATIVO("ATIVO"),
    INATIVO("INATIVO"),
    ABERTO("ABERTO"),
    FECHADO("FECHADO");

    private String value;

    Situacao(String str) {
    }
    public String getValue() {
        return value;
    }
}
