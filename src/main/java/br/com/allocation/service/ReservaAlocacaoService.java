package br.com.allocation.service;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.avaliacaoDTO.AvaliacaoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.entity.ReservaAlocacaoEntity;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.StatusAluno;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AlunoRepository;
import br.com.allocation.repository.ReservaAlocacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaAlocacaoService {
    private final ReservaAlocacaoRepository reservaAlocacaoRepository;
    private final AlunoService alunoService;
    private final AlunoRepository alunoRepository;
    private final VagaService vagaService;
    private final AvaliacaoService avaliacaoService;


    public ReservaAlocacaoDTO salvar(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);
        AlunoEntity aluno = reservaAlocacaoEntity.getAluno();
        alunoService.alterarStatusAluno(reservaAlocacaoCreateDTO.getIdAluno(),
                reservaAlocacaoCreateDTO);
        ReservaAlocacaoEntity saveAlocacaoReserva = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
        aluno.setReservaAlocacao(saveAlocacaoReserva);
        ReservaAlocacaoDTO reservaAlocacaoDTO = converterEmDTO(saveAlocacaoReserva);
        vagaService.alterarQuantidadeDeVagas(reservaAlocacaoCreateDTO.getIdVaga());
        return reservaAlocacaoDTO;
    }

    public ReservaAlocacaoDTO editar(Integer codigo,
                                     ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        this.findById(codigo);
        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);
        reservaAlocacaoEntity.setCodigo(codigo);
        AlunoEntity aluno = reservaAlocacaoEntity.getAluno();
        ReservaAlocacaoEntity saveAlocacaoReserva = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
        aluno.setReservaAlocacao(saveAlocacaoReserva);
        vagaService.alterarQuantidadeDeVagas(reservaAlocacaoCreateDTO.getIdVaga());
        return converterEmDTO(saveAlocacaoReserva);
    }

    public void deletar(Integer id) throws RegraDeNegocioException {
        ReservaAlocacaoEntity reservaLocacaoDelete = this.findById(id);
        reservaAlocacaoRepository.deleteById(reservaLocacaoDelete.getCodigo());
        reservaLocacaoDelete.getAluno().setStatusAluno(StatusAluno.DISPONIVEL);
        alunoRepository.save(reservaLocacaoDelete.getAluno());
    }

    public PageDTO<ReservaAlocacaoDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage = reservaAlocacaoRepository
                .findAll(pageRequest);

        List<ReservaAlocacaoDTO> reservaAlocacaoDTOList = reservaAlocacaoEntityPage
                .getContent().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

        return new PageDTO<>(reservaAlocacaoEntityPage.getTotalElements(),
                reservaAlocacaoEntityPage.getTotalPages(),
                pagina,
                tamanho,
                reservaAlocacaoDTOList);
    }


    public ReservaAlocacaoEntity findById(Integer id) throws RegraDeNegocioException {
        ReservaAlocacaoEntity alocacaoEntity = reservaAlocacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada!"));
        return alocacaoEntity;
    }

    private ReservaAlocacaoEntity converterEntity(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = alunoService.findById(reservaAlocacaoCreateDTO.getIdAluno());
        alunoService.verificarDisponibilidadeAluno(alunoEntity, reservaAlocacaoCreateDTO);
        alunoService.verificarSeAlunoTemReserva(alunoEntity,reservaAlocacaoCreateDTO);
        VagaEntity vagaEntity = vagaService.findById(reservaAlocacaoCreateDTO.getIdVaga());
        AvaliacaoEntity avaliacaoEntity = avaliacaoService.findById(reservaAlocacaoCreateDTO.getIdAvaliacao());

        if (reservaAlocacaoCreateDTO.getDataCancelamento() != null || reservaAlocacaoCreateDTO.getDataFinalizado() != null) {
            alunoService.alterarStatusAlunoCancelado(alunoEntity.getIdAluno(), reservaAlocacaoCreateDTO);
        }


        ReservaAlocacaoEntity reservaAlocacaoEntity = new ReservaAlocacaoEntity(null,
                reservaAlocacaoCreateDTO.getDescricao(),
                reservaAlocacaoCreateDTO.getDataReserva(),
                reservaAlocacaoCreateDTO.getDataAlocacao(),
                reservaAlocacaoCreateDTO.getDataCancelamento(),
                null,
                alunoEntity.getStatusAluno(),
                alunoEntity,
                vagaEntity,
                avaliacaoEntity);
        return reservaAlocacaoEntity;
    }

    private ReservaAlocacaoDTO converterEmDTO(ReservaAlocacaoEntity reservaAlocacaoEntity) {
        VagaDTO vagaDTO = vagaService.converterEmDTO(reservaAlocacaoEntity.getVaga());
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.converterEmDTO(reservaAlocacaoEntity.getAvaliacao());
        AlunoDTO alunoDTO = alunoService.converterEmDTO(reservaAlocacaoEntity.getAluno());
        ReservaAlocacaoDTO reservaAlocacaoDTO = new ReservaAlocacaoDTO(reservaAlocacaoEntity.getCodigo(),
                vagaDTO,
                alunoDTO,
                avaliacaoDTO,
                reservaAlocacaoEntity.getStatusAluno(),
                reservaAlocacaoEntity.getDescricao(),
                reservaAlocacaoEntity.getDataReserva(),
                reservaAlocacaoEntity.getDataAlocacao(),
                reservaAlocacaoEntity.getDataCancelamento(),
                reservaAlocacaoEntity.getDataFinalizado());
        return reservaAlocacaoDTO;
    }

}
