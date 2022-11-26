package br.com.allocation.enums;

public enum Cargos {
    ADMINISTRADOR("ROLE_ADMINISTRADOR"),
    GESTOR("ROLE_GESTOR"),
    GESTAO_DE_PESSOAS("ROLE_GESTAO_DE_PESSOAS"),
    INSTRUTOR("ROLE_INSTRUTOR");

    private String descricao;

    Cargos(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }
}
