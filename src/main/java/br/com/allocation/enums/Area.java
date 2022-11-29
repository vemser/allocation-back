package br.com.allocation.enums;

public enum Area {
    FRONTEND("FRONTEND"),
    BACKEND("BACKEND"),
    QA("QA");

    private String descricao;

    Area(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }
}
