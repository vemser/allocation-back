package br.com.allocation.enums;

public enum Avaliacao {
    AVALIACAO1("AVALIACAO1"),
    AVALIACAO2("AVALIACAO2"),
    AVALIACAO3("AVALIACAO3");

    private String value;
    Avaliacao(String str) {
    }
    public String getValue() {
        return value;
    }
}
