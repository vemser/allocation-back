package br.com.allocation.service.factory;

import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.enums.Situacao;

import java.time.LocalDate;
import java.util.Collections;

public class ProgramaFactory {
    public static ProgramaEntity getProgramaEntity(){
        ProgramaEntity programaEntity = new ProgramaEntity(1,
                "VemSer 10ed",
                "Programa de formação profissional trilha Backend Vem Ser DBC 10º edição.",
                LocalDate.now(),
                LocalDate.now().plusMonths(3),
                Situacao.ABERTO,
                Collections.emptySet(),
                Collections.emptySet());
        return programaEntity;
    }
}
