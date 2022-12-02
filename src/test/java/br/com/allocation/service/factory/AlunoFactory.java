package br.com.allocation.service.factory;

import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.enums.Area;
import br.com.allocation.enums.StatusAluno;

import java.util.Collections;

public class AlunoFactory {
    public static AlunoEntity getAlunoEntity(){
        AlunoEntity alunoEntity = new AlunoEntity(1,
                "Gustavo Lucena",
                "jhennyfer.sobrinho@dbccompany.com.br",
                Area.FRONTEND,
                "Pato Branco",
                "Paraná",
                "99595-1313",
                "xxx",
                StatusAluno.DISPONIVEL,
                Collections.emptySet(),
                null,
                Collections.emptySet(),
                null);
        return alunoEntity;
    }
}
