package br.com.allocation.enums;

public enum StatusAluno {
    ALOCADO("ALOCADO"),
    DISPONIVEL("DISPONIVEL"),
    RESERVADO("RESERVADO");

    private String value;

    StatusAluno(String str) {
    }
    public String getValue() {
        return value;
    }

}
