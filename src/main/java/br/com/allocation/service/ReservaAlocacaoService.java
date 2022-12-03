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
        vagaService.alterarQuantidadeDeVagas(reservaAlocacaoCreateDTO.getIdVaga());
        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);

        AlunoEntity aluno = reservaAlocacaoEntity.getAluno();
        alunoService.verificarDisponibilidadeAluno(reservaAlocacaoEntity.getAluno(), reservaAlocacaoCreateDTO);

        alunoService.alterarStatusAluno(reservaAlocacaoCreateDTO.getIdAluno(),
                reservaAlocacaoCreateDTO);
        aluno.setStatusAluno(StatusAluno.RESERVADO);
        alunoRepository.save(aluno);

        adicionarQtdAlocadosEmVagas(reservaAlocacaoCreateDTO, reservaAlocacaoEntity, aluno);
        ReservaAlocacaoEntity saveAlocacaoReserva = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
        aluno.getReservaAlocacaos().add(saveAlocacaoReserva);
        vagaService.alterarQuantidadeDeVagas(reservaAlocacaoCreateDTO.getIdVaga());

        try {
            reservaAlocacaoEntity = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new RegraDeNegocioException("Erro ao resevar, aluno já cadastrado!");
        }

        return converterEmDTO(reservaAlocacaoEntity);
    }

    public ReservaAlocacaoDTO editar(Integer idReserva,
                                     ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        this.findById(idReserva);
        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);
        reservaAlocacaoEntity.setIdReservaAlocacao(idReserva);
        AlunoEntity aluno = reservaAlocacaoEntity.getAluno();
        ReservaAlocacaoEntity saveAlocacaoReserva = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
        aluno.getReservaAlocacaos().add(saveAlocacaoReserva);

        aluno.getReservaAlocacaos().add(reservaAlocacaoEntity);

        adicionarQtdAlocadosEmVagas(reservaAlocacaoCreateDTO, reservaAlocacaoEntity, aluno);
        if (reservaAlocacaoCreateDTO.getStatusAluno().equals(StatusAluno.RESERVADO)) {
            if (reservaAlocacaoCreateDTO.getIdVaga().equals(reservaAlocacaoEntity.getVaga().getIdVaga())) {
                alunoService.alterarStatusAluno(aluno.getIdAluno(), reservaAlocacaoCreateDTO);
            }
        }

        return converterEmDTO(saveAlocacaoReserva);
    }

    public PageDTO<ReservaAlocacaoDTO> filtrar(Integer pagina, Integer tamanho, String nomeAluno, String nomeVaga) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage = reservaAlocacaoRepository
                .findAllByFiltro(pageRequest, nomeAluno, nomeVaga);

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

    public void deletar(Integer id) throws RegraDeNegocioException {
        ReservaAlocacaoEntity reservaAlocacao = findById(id);
        AlunoEntity aluno = reservaAlocacao.getAluno();
        reservaAlocacao.setStatusAluno(StatusAluno.FINALIZADO);
        aluno.setStatusAluno(StatusAluno.DISPONIVEL);
        alunoRepository.save(aluno);
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
        return reservaAlocacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada!"));
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
                reservaAlocacaoCreateDTO.getStatusAluno(),
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
                reservaAlocacaoEntity.getStatusAluno(),
                reservaAlocacaoEntity.getDescricao(),
                reservaAlocacaoEntity.getDataReserva(),
                reservaAlocacaoEntity.getDataAlocacao(),
                reservaAlocacaoEntity.getDataCancelamento(),
                reservaAlocacaoEntity.getDataFinalizado());
    }

    public void adicionarQtdAlocadosEmVagas (ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO,
                ReservaAlocacaoEntity reservaAlocacaoEntity,
                AlunoEntity aluno) throws RegraDeNegocioException {
            if (reservaAlocacaoCreateDTO.getStatusAluno().equals(StatusAluno.ALOCADO)) {
                alunoService.alterarStatusAluno(aluno.getIdAluno(), reservaAlocacaoCreateDTO);
                vagaService.adicionarQuantidadeDeAlocados(reservaAlocacaoEntity.getVaga().getIdVaga());
            }
        }

}
