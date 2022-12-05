package br.com.allocation.service;

import br.com.allocation.dto.clienteDTO.ClienteDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.vagaDTO.VagaCreateDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.entity.ClienteEntity;
import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.Situacao;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;
    private final ProgramaService programaService;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;


    public VagaDTO salvar(VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        ProgramaEntity programa = programaService.findById(vagaCreate.getIdPrograma());
        ClienteEntity cliente = clienteService.findById(vagaCreate.getIdCliente());
        bloquearAlteracaoEmQuantAlocados(vagaCreate);
        vagaCreate.setQuantidadeAlocados(0);
        vagaCreate.setSituacao(Situacao.ABERTO);
        VagaEntity vagaEntity = converterEntity(vagaCreate);
        vagaEntity.setCliente(cliente);
        vagaEntity.setPrograma(programa);
        vagaEntity.setDataCriacao(LocalDate.now());

        vagaEntity = vagaRepository.save(vagaEntity);
        verificarClienteInativo(vagaEntity);
        return converterEmDTO(vagaEntity);
    }

    private static void bloquearAlteracaoEmQuantAlocados(VagaCreateDTO vagaCreate) {
        if (vagaCreate.getQuantidadeAlocados() != null) {
            vagaCreate.setQuantidadeAlocados(0);
        }
    }

    public PageDTO<VagaDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<VagaEntity> paginaDoRepositorio = vagaRepository.findAll(pageRequest);

        List<VagaDTO> vagas = paginaDoRepositorio.getContent().stream()
                .map(this::converterEmDTO)
                .toList();

        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                pagina,
                tamanho,
                vagas
        );
    }


    public PageDTO<VagaDTO> listarPorId(Integer idVaga) throws RegraDeNegocioException {
        List<VagaDTO> list = List.of(converterEmDTO(findById(idVaga)));
        Page<VagaDTO> page = new PageImpl<>(list);

        return new PageDTO<>(page.getTotalElements(),
                page.getTotalPages(),
                0,
                1,
                list
        );
    }

    public PageDTO<VagaDTO> listarPorNome(Integer pagina, Integer tamanho, String nome) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<VagaEntity> paginaDoRepositorio = vagaRepository.findAllByNomeContainingIgnoreCase(pageRequest, nome);

        List<VagaDTO> vagas = paginaDoRepositorio.getContent().stream()
                .map(this::converterEmDTO)
                .toList();

        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                pagina,
                tamanho,
                vagas
        );
    }

    public VagaDTO editar(Integer idVaga, VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        VagaEntity vagaEntity1 = findById(idVaga);
        vagaCreate.setQuantidadeAlocados(0);
        if (vagaCreate.getSituacao().equals(Situacao.FECHADO)) {
            fecharVaga(vagaEntity1);
        }
        VagaEntity vagaEntity = objectMapper.convertValue(vagaCreate, VagaEntity.class);

        bloquearAlteracaoEmQuantAlocados(vagaCreate);

        ProgramaEntity programa = programaService.findById(vagaCreate.getIdPrograma());
        ClienteEntity cliente = clienteService.findById(vagaCreate.getIdCliente());
        vagaEntity.setCliente(cliente);
        vagaEntity.setPrograma(programa);
        vagaEntity.setIdVaga(idVaga);
        vagaEntity.setDataCriacao(LocalDate.now());

        vagaEntity = vagaRepository.save(vagaEntity);

        return converterEmDTO(vagaRepository.save(vagaEntity));
    }

    public VagaDTO converterEmDTO(VagaEntity vagaEntity) {

        ClienteDTO clienteDTO = clienteService.converterEmDTO(vagaEntity.getCliente());
        VagaDTO vagaDTO = new VagaDTO(vagaEntity.getIdVaga(),
                vagaEntity.getNome(),
                vagaEntity.getQuantidade(),
                vagaEntity.getQuantidadeAlocados(),
                vagaEntity.getIdPrograma(),
                vagaEntity.getSituacao(),
                vagaEntity.getDataAbertura(),
                vagaEntity.getDataFechamento(),
                vagaEntity.getDataCriacao(),
                clienteDTO,
                vagaEntity.getObservacoes());
        return vagaDTO;
    }

    public VagaEntity converterEntity(VagaCreateDTO vagaCreateDTO) {
        return objectMapper.convertValue(vagaCreateDTO, VagaEntity.class);
    }

    public void deletar(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        vagaRepository.delete(vaga);
    }

    public VagaEntity findById(Integer idVaga) throws RegraDeNegocioException {
        return vagaRepository.findById(idVaga).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada!"));
    }

    public List<VagaDTO> findAllWithSituacaoAberto() {
        return vagaRepository.findBySituacao(Situacao.ABERTO)
                .stream()
                .map(vagaEntity -> objectMapper.convertValue(vagaEntity, VagaDTO.class))
                .collect(Collectors.toList());
    }

    public VagaEntity findByNome(String nome) throws RegraDeNegocioException {
        return vagaRepository.findByNome(nome).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada!"));
    }

    public void alterarQuantidadeDeVagas(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        verificarClienteInativo(vaga);
        if (vaga.getQuantidade() > 0) {
            vaga.setQuantidade(vaga.getQuantidade() - 1);
            vagaRepository.save(vaga);
        } else {
            throw new RegraDeNegocioException("Quantidades de Vagas foram prenchidas!");
        }
    }


    private static void verificarClienteInativo(VagaEntity vaga) throws RegraDeNegocioException {
        if (vaga.getCliente().getSituacao().equals(Situacao.INATIVO)) {
            throw new RegraDeNegocioException("Cliente inativo!");
        }
    }

    public void fecharVaga(VagaEntity vaga) {
        if (vaga.getQuantidade() == 0) {
            vaga.setSituacao(Situacao.FECHADO);
            vagaRepository.save(vaga);
        }
    }


    public void adicionarQuantidadeDeAlocados(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        vaga.setQuantidadeAlocados(vaga.getQuantidadeAlocados() + 1);
        vagaRepository.save(vaga);
    }
    public void removerQuantidadeDeAlocados(Integer idVaga) throws RegraDeNegocioException {
        VagaEntity vaga = findById(idVaga);
        vaga.setQuantidadeAlocados(vaga.getQuantidadeAlocados() - 1);
        vagaRepository.save(vaga);
    }


}
