package br.com.allocation.enums;

public enum SituacaoAluno {

    AVALIADO("AVALIADO"),
    AGENDADO("AGENDADO"),
    ENTREVISTADO("ENTREVISTADO"),
    APROVADO("APROVADO"),
    REPROVADO("REPROVADO"),
    CANCELADO("CANCELADO"),
    FINALIZADO("FINALIZADO");

    private String value;
    SituacaoAluno(String str) {
    }
    public String getValue() {
        return value;
    }
}
