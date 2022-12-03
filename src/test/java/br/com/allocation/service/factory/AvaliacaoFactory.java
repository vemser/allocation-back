package br.com.allocation.service.factory;

import br.com.allocation.dto.avaliacaoDTO.AvaliacaoCreateDTO;
import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.enums.SituacaoAluno;
import br.com.allocation.enums.TipoAvaliacao;

import java.time.LocalDate;
import java.util.Collections;

public class AvaliacaoFactory {
    public static AvaliacaoEntity getAvalicaoEntity(){
        AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity(1, 19,
                10,"top",9.5, TipoAvaliacao.INDIVIDUAL,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(4),
                LocalDate.now().plusDays(4),
                LocalDate.now().plusDays(2),
                SituacaoAluno.AGENDADO_CLIENTE,AlunoFactory.getAlunoEntity(),
                VagaFactory.getVagaEntity(),
                Collections.emptySet());
        return avaliacaoEntity;
    }

    public static AvaliacaoCreateDTO getAvaliacaoCreateDTO(){
        AvaliacaoCreateDTO avaliacaoCreateDTO = new AvaliacaoCreateDTO();
        avaliacaoCreateDTO.setIdVaga(1);
        avaliacaoCreateDTO.setEmailAluno("kaio@bol.com");
        avaliacaoCreateDTO.setDataAvaliacao(LocalDate.now());
        avaliacaoCreateDTO.setTipoAvaliacao(TipoAvaliacao.INDIVIDUAL);
        avaliacaoCreateDTO.setDataCriacao(LocalDate.now());
        avaliacaoCreateDTO.setDescricao("xxxx");
        avaliacaoCreateDTO.setNota(10);
        avaliacaoCreateDTO.setSituacao(String.valueOf(SituacaoAluno.APROVADO));
        return avaliacaoCreateDTO;
    }
}
