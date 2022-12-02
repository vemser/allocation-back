package br.com.allocation.service;

import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.programaDTO.ProgramaCreateDTO;
import br.com.allocation.dto.programaDTO.ProgramaDTO;
import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.enums.Situacao;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.ProgramaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramaService {

    private final ProgramaRepository programaRepository;
    private final ObjectMapper objectMapper;


    public ProgramaDTO salvar(ProgramaCreateDTO programaCreate) {
        ProgramaEntity programaEntity = objectMapper.convertValue(programaCreate, ProgramaEntity.class);
        programaEntity.setSituacao(Situacao.valueOf(programaCreate.getSituacao()));

        return objectMapper.convertValue(programaRepository.save(programaEntity), ProgramaDTO.class);
    }

    public PageDTO<ProgramaDTO> listar(Integer pagina, Integer tamanho){
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ProgramaEntity> paginaRepository = programaRepository.findAll(pageRequest);

        List<ProgramaDTO> ClientePagina = paginaRepository.getContent().stream()
                .map(x -> objectMapper.convertValue(x, ProgramaDTO.class))
                .toList();

        return new PageDTO<>(paginaRepository.getTotalElements(), paginaRepository.getTotalPages(), pagina, tamanho, ClientePagina);
    }

    public PageDTO<ProgramaDTO> listarPorNome(Integer pagina, Integer tamanho, String nome){
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ProgramaEntity> paginaRepository = programaRepository.findAllByNomeContainingIgnoreCase(nome, pageRequest);

        List<ProgramaDTO> ClientePagina = paginaRepository.getContent().stream()
                .map(x -> objectMapper.convertValue(x, ProgramaDTO.class))
                .toList();

        return new PageDTO<>(paginaRepository.getTotalElements(), paginaRepository.getTotalPages(), pagina, tamanho, ClientePagina);
    }

    public ProgramaDTO listarPorId(Integer idPrograma) throws RegraDeNegocioException {
        return objectMapper.convertValue(findById(idPrograma), ProgramaDTO.class);
    }

    public ProgramaDTO editar(Integer idPrograma, ProgramaCreateDTO programaCreate) throws RegraDeNegocioException {
        ProgramaEntity programaEntity = findById(idPrograma);
        programaEntity.setSituacao(Situacao.valueOf(programaCreate.getSituacao()));
        programaEntity.setNome(programaCreate.getNome());
        programaEntity.setDescricao(programaCreate.getDescricao());
        programaEntity.setDataCriacao(programaCreate.getDataCriacao());
        programaEntity.setDataTermino(programaCreate.getDataTermino());

        programaRepository.save(programaEntity);
        return objectMapper.convertValue(programaEntity, ProgramaDTO.class);
    }

    public void deletar(Integer idPrograma) throws RegraDeNegocioException {
        ProgramaEntity programaEntity = findById(idPrograma);
        try {
            programaRepository.delete(programaEntity);
        }catch (DataIntegrityViolationException ex){
            throw new RegraDeNegocioException("Não é possivel deletar pois existem registros atrelados a esse programa.");
        }
    }

    public ProgramaEntity findById(Integer id) throws RegraDeNegocioException {
        return programaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Programa não encontrado"));
    }

    public ProgramaEntity findByNome(String nome) throws RegraDeNegocioException {
        return programaRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new RegraDeNegocioException("Programa não encontrado"));
    }
}
