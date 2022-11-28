package br.com.allocation.enums;

public enum SituacaoPrograma {
    ABERTO(1),FECHADO(2);

    private int value;
    SituacaoPrograma(int i) {
    }
    public int getValue() {
        return value;
    }
}
