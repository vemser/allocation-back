package br.com.allocation.enums;

public enum TipoAvaliacao {
    INDIVIDUAL("INDIVIDUAL"),
    CLIENTE("CLIENTE");

    private String value;

    TipoAvaliacao(String str) {
    }
    public String getValue() {
        return value;
    }
}
