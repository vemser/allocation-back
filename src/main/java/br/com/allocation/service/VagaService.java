package br.com.allocation.service;

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
        VagaEntity vaga = objectMapper.convertValue(vagaCreate, VagaEntity.class);
        ProgramaEntity programa = programaService.findById(vagaCreate.getIdPrograma());
        ClienteEntity cliente = clienteService.findByEmail(vagaCreate.getEmailCliente());

        vaga.setPrograma(programa);
        vaga.setSituacao(Situacao.valueOf(vagaCreate.getSituacao()));
        vaga.setCliente(cliente);
        vaga.setQuantidadeAlocados(QUANTIDADE_INICIAL_ALOCADO);

        vagaRepository.save(vaga);

        VagaDTO vagaDto = objectMapper.convertValue(vaga, VagaDTO.class);
        vagaDto.setIdPrograma(programa.getIdPrograma());
        vagaDto.setEmailCliente(cliente.getEmail());
        return vagaDto;
    }

    public PageDTO<VagaDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<VagaEntity> paginaDoRepositorio = vagaRepository.findAll(pageRequest);

        List<VagaDTO> vagas = paginaDoRepositorio.getContent().stream()
                .map(vaga  -> {
                    VagaDTO vagaDto = objectMapper.convertValue(vaga, VagaDTO.class);
                    vagaDto.setIdPrograma(vaga.getPrograma().getIdPrograma());
                    vagaDto.setEmailCliente(vaga.getCliente().getEmail());

                    return vagaDto;
                })
                .toList();

        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                pagina,
                tamanho,
                vagas
        );
    }

    public VagaDTO editar(Integer codigo, VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        VagaEntity vagaEntity = findById(codigo);
        vagaEntity = objectMapper.convertValue(vagaCreate, VagaEntity.class);
        ProgramaEntity programa = programaService.findById(vagaCreate.getIdPrograma());
        vagaEntity.setPrograma(programa);
        vagaEntity.setCodigo(codigo);

        vagaEntity = vagaRepository.save(vagaEntity);

        VagaDTO vagaDto = converterEmDTO(vagaEntity);
        vagaDto.setIdPrograma(programa.getIdPrograma());
        return vagaDto;
    }
    public VagaDTO converterEmDTO(VagaEntity vagaEntity){
        return objectMapper.convertValue(vagaEntity, VagaDTO.class);
    }

    public void deletar(Integer codigo) throws RegraDeNegocioException {
        VagaEntity vaga = findById(codigo);
        vagaRepository.delete(vaga);
    }

    public VagaDTO pegarVaga(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(findById(id), VagaDTO.class);
    }

    public VagaEntity findById(Integer id) throws RegraDeNegocioException {
        return vagaRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada!"));
    }

    public List<VagaDTO> findAllWithSituacaoAberto(){
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
}
