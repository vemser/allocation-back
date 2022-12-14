package br.com.allocation.service;

import br.com.allocation.dto.alunodto.AlunoDTO;
import br.com.allocation.dto.avaliacaodto.AvaliacaoDTO;
import br.com.allocation.dto.pagedto.PageDTO;
import br.com.allocation.dto.reservaAlocacaodto.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaodto.ReservaAlocacaoDTO;
import br.com.allocation.dto.vagadto.VagaDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.entity.ReservaAlocacaoEntity;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.SituacaoAllocation;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AlunoRepository;
import br.com.allocation.repository.ReservaAlocacaoRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
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
        alunoService.verificarDisponibilidadeAluno(reservaAlocacaoEntity.getAluno(), reservaAlocacaoCreateDTO);

        alunoService.alterarStatusAluno(reservaAlocacaoCreateDTO.getIdAluno(),
                reservaAlocacaoCreateDTO);
        aluno.setSituacaoAllocation(SituacaoAllocation.RESERVADO);
        alunoRepository.save(aluno);

        try {
            adicionarQtdAlocadosEmVagas(reservaAlocacaoCreateDTO, reservaAlocacaoEntity, aluno);
            ReservaAlocacaoEntity saveAlocacaoReserva = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
            aluno.getReservaAlocacaos().add(saveAlocacaoReserva);
            reservaAlocacaoEntity = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
            verificarAlunoReservado(reservaAlocacaoEntity.getAluno(), reservaAlocacaoCreateDTO);
        } catch (DataIntegrityViolationException ex) {
            throw new RegraDeNegocioException("Erro ao resevar, aluno j?? cadastrado!");
        }

        return converterEmDTO(reservaAlocacaoEntity);
    }

    public ReservaAlocacaoDTO editar(Integer idReserva, ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        this.findById(idReserva);

        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);
        reservaAlocacaoEntity.setIdReservaAlocacao(idReserva);

        AlunoEntity aluno = reservaAlocacaoEntity.getAluno();
        ReservaAlocacaoEntity saveAlocacaoReserva = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
        aluno.getReservaAlocacaos().add(saveAlocacaoReserva);

        adicionarQtdAlocadosEmVagas(reservaAlocacaoCreateDTO, reservaAlocacaoEntity, aluno);
        if (reservaAlocacaoCreateDTO.getSituacaoAllocation().equals(SituacaoAllocation.RESERVADO)) {
            if (reservaAlocacaoCreateDTO.getIdVaga().equals(reservaAlocacaoEntity.getVaga().getIdVaga())) {
                alunoService.alterarStatusAluno(aluno.getIdAluno(), reservaAlocacaoCreateDTO);
            }
        }

        return converterEmDTO(saveAlocacaoReserva);
    }

    public void verificarAlunoReservado(AlunoEntity alunoEntity,
                                        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        if (!alunoEntity.getSituacaoAllocation().equals(SituacaoAllocation.RESERVADO)) {
            vagaService.alterarQuantidadeDeVagas(reservaAlocacaoCreateDTO.getIdVaga());
        }
    }

    public PageDTO<ReservaAlocacaoDTO> filtrar(Integer pagina, Integer tamanho, String nomeAluno, String nomeVaga) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);

        nomeAluno = nomeAluno != null ? "%" + nomeAluno + "%" : null;
        nomeVaga = nomeVaga != null ? "%" + nomeVaga + "%" : null;


        Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage = reservaAlocacaoRepository
                .findAllByFiltro(pageRequest, nomeAluno, nomeVaga);

        List<ReservaAlocacaoDTO> reservaAlocacaoDTOList = getReservaAlocacaoDTOS(reservaAlocacaoEntityPage);

        return new PageDTO<>(reservaAlocacaoEntityPage.getTotalElements(),
                reservaAlocacaoEntityPage.getTotalPages(),
                pagina,
                tamanho,
                reservaAlocacaoDTOList);
    }

    @NotNull
    private List<ReservaAlocacaoDTO> getReservaAlocacaoDTOS(Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage) {
        List<ReservaAlocacaoDTO> reservaAlocacaoDTOList = reservaAlocacaoEntityPage
                .getContent().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        return reservaAlocacaoDTOList;
    }

    public void deletar(Integer id) throws RegraDeNegocioException {
        ReservaAlocacaoEntity reservaAlocacao = findById(id);
        AlunoEntity aluno = reservaAlocacao.getAluno();

        reservaAlocacao.setSituacaoAllocation(SituacaoAllocation.INATIVO);
        aluno.setSituacaoAllocation(SituacaoAllocation.DISPONIVEL);
        alunoRepository.save(aluno);
    }

    public PageDTO<ReservaAlocacaoDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage = reservaAlocacaoRepository
                .findAll(pageRequest);

        List<ReservaAlocacaoDTO> reservaAlocacaoDTOList = getReservaAlocacaoDTOS(reservaAlocacaoEntityPage);

        return new PageDTO<>(reservaAlocacaoEntityPage.getTotalElements(),
                reservaAlocacaoEntityPage.getTotalPages(),
                pagina,
                tamanho,
                reservaAlocacaoDTOList);
    }

    public ReservaAlocacaoEntity findById(Integer id) throws RegraDeNegocioException {
        return reservaAlocacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva n??o encontrada!"));
    }

    private ReservaAlocacaoEntity converterEntity(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = alunoService.findById(reservaAlocacaoCreateDTO.getIdAluno());
        VagaEntity vagaEntity = vagaService.findById(reservaAlocacaoCreateDTO.getIdVaga());
        AvaliacaoEntity avaliacaoEntity = avaliacaoService.findById(reservaAlocacaoCreateDTO.getIdAvaliacao());

        if (reservaAlocacaoCreateDTO.getDataCancelamento() != null || reservaAlocacaoCreateDTO.getDataFinalizado() != null) {
            alunoService.alterarStatusAlunoCancelado(alunoEntity.getIdAluno(), reservaAlocacaoCreateDTO);
        }

        return new ReservaAlocacaoEntity(null,
                reservaAlocacaoCreateDTO.getIdAluno(),
                reservaAlocacaoCreateDTO.getDescricao(),
                reservaAlocacaoCreateDTO.getDataReserva(),
                reservaAlocacaoCreateDTO.getDataAlocacao(),
                reservaAlocacaoCreateDTO.getDataCancelamento(),
                null,
                reservaAlocacaoCreateDTO.getSituacaoAllocation(),
                alunoEntity,
                vagaEntity,
                avaliacaoEntity);
    }

    private ReservaAlocacaoDTO converterEmDTO(ReservaAlocacaoEntity reservaAlocacaoEntity) {
        VagaDTO vagaDTO = vagaService.converterEmDTO(reservaAlocacaoEntity.getVaga());
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.converterEmDTO(reservaAlocacaoEntity.getAvaliacao());
        AlunoDTO alunoDTO = alunoService.converterEmDTO(reservaAlocacaoEntity.getAluno());

        return new ReservaAlocacaoDTO(reservaAlocacaoEntity.getIdReservaAlocacao(),
                vagaDTO,
                alunoDTO,
                avaliacaoDTO,
                reservaAlocacaoEntity.getSituacaoAllocation(),
                reservaAlocacaoEntity.getDescricao(),
                reservaAlocacaoEntity.getDataReserva(),
                reservaAlocacaoEntity.getDataAlocacao(),
                reservaAlocacaoEntity.getDataCancelamento(),
                reservaAlocacaoEntity.getDataFinalizado());
    }

    public void adicionarQtdAlocadosEmVagas(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO,
                                            ReservaAlocacaoEntity reservaAlocacaoEntity,
                                            AlunoEntity aluno) throws RegraDeNegocioException {
        if (reservaAlocacaoCreateDTO.getSituacaoAllocation().equals(SituacaoAllocation.ALOCADO)) {
            alunoService.alterarStatusAluno(aluno.getIdAluno(), reservaAlocacaoCreateDTO);
            vagaService.adicionarQuantidadeDeAlocados(reservaAlocacaoEntity.getVaga().getIdVaga());
        }
    }

}
