package br.com.allocation.enums;

public enum Area {
    FRONT(1),
    BACK(2),
    QA(3);

    private int value;
    Area(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }
}
