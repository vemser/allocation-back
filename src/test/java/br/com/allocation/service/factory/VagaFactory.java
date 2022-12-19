package br.com.allocation.service.factory;

import br.com.allocation.dto.vagadto.VagaCreateDTO;
import br.com.allocation.dto.vagadto.VagaDTO;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.Situacao;

import java.time.LocalDate;
import java.util.Collections;

public class VagaFactory {
    public static VagaEntity getVagaEntity() {
        VagaEntity vagaEntity = new VagaEntity(2,
                95,
                "Desenvolvedor(a) Java - Back-End",
                10,
                0,
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(20),
                Situacao.ABERTO,
                "Seguir conceitos de programação como: Alta coesão, Baixo acoplamento, e componentização.",
                ClienteFactory.getClienteEntity(),
                Collections.emptySet(),
                ProgramaFactory.getProgramaEntity(),
                Collections.emptySet());
        return vagaEntity;
    }

    public static VagaCreateDTO getvagaCreateDTO() {
        VagaCreateDTO vagaCreateDTO = new VagaCreateDTO("vaga",
                10,
                0,
                10,
                Situacao.ATIVO,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(20),
                LocalDate.now(),
                "xx",
                ClienteFactory.getClienteEntity().getIdCliente());
        return vagaCreateDTO;
    }

    public static VagaDTO getVagaDTO() {
        VagaDTO vagaDTO = new VagaDTO();
        vagaDTO.setIdVaga(1);
        vagaDTO.setSituacao(Situacao.ABERTO);
        vagaDTO.setDataAbertura(LocalDate.now());
        vagaDTO.setDataCriacao(LocalDate.now());
        vagaDTO.setDataFechamento(LocalDate.now());
        return vagaDTO;
    }

}
