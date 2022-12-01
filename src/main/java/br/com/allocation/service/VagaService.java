package br.com.allocation.service;

import br.com.allocation.dto.clienteDTO.ClienteDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.vagaDTO.VagaCreateDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.Situacao;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;
    private final ProgramaService programaService;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;

    private final static Integer QUANTIDADE_INICIAL_ALOCADO = 0;

    public VagaDTO salvar(VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        programaService.findById(vagaCreate.getIdPrograma());
        vagaCreate.setQuantidadeAlocados(QUANTIDADE_INICIAL_ALOCADO);
        VagaEntity vagaEntity1 = converterEntity(vagaCreate);
        VagaEntity vagaEntity = vagaRepository.save(vagaEntity1);
        return converterEmDTO(vagaEntity);
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

    public VagaDTO listarPorId(Integer idVaga) throws RegraDeNegocioException {
        return converterEmDTO(findById(idVaga));
    }

    public VagaDTO editar(Integer idVaga, VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        this.findById(idVaga);
        programaService.findById(vagaCreate.getIdPrograma());
        VagaEntity vagaEntity1 = objectMapper.convertValue(vagaCreate,VagaEntity.class);
        return converterEmDTO(vagaRepository.save(vagaEntity1));
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
                clienteDTO);
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
        if (vaga.getQuantidade() >= 1) {
            vaga.setQuantidade(vaga.getQuantidade() - 1);
            vagaRepository.save(vaga);
        } else if (vaga.getQuantidade() == 0) {
            throw new RegraDeNegocioException("Quantidades de Vagas foram prenchidas!");
        }
    }
//    public void verificarAlocado(Integer idVagaEntity, Integer idVagaDTO){
//        if (!(idVagaDTO.equals(idVagaEntity))) {
//            vaga.setQuantidade(vaga.getQuantidade() - 1);
//            vagaRepository.save(vaga);
//        }
//    }

}
