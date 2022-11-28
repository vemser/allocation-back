package br.com.allocation.enums;

public enum StatusAluno {
    ALOCADO(1), DISPONIVEL(2), RESERVADO(3);

    private int value;


    StatusAluno(int i) {
    }

    public int getValue() {
        return value;
    }

}
