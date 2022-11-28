package br.com.allocation.service;

import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.vagaDTO.VagaCreateDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;
    private final ProgramaService programaService;
    private final ObjectMapper objectMapper;

    public VagaDTO salvar(VagaCreateDTO vagaCreate) throws RegraDeNegocioException {
        VagaEntity vaga = objectMapper.convertValue(vagaCreate, VagaEntity.class);
        ProgramaEntity programa = programaService.finbByNome(vagaCreate.getProgramaDTO().getNome());
        vaga.setPrograma(programa);

        vaga = vagaRepository.save(vaga);
        VagaDTO vagaDto = objectMapper.convertValue(vaga, VagaDTO.class);
        vagaDto.setProgramaDTO(programa.getNome());
        return vagaDto;
    }

    public PageDTO<VagaDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<VagaEntity> paginaDoRepositorio = vagaRepository.findAll(pageRequest);

        List<VagaDTO> vagas = paginaDoRepositorio.getContent().stream()
                .map(vaga  -> objectMapper.convertValue(vaga, VagaDTO.class))
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
        ProgramaEntity programa = programaService.finbByNome(vagaCreate.getProgramaDTO().getNome());
        vagaEntity.setPrograma(programa);
        vagaEntity.setCodigo(codigo);

        vagaEntity = vagaRepository.save(vagaEntity);

        VagaDTO vagaDto = objectMapper.convertValue(vagaEntity, VagaDTO.class);
        vagaDto.setProgramaDTO(programa.getNome());
        return vagaDto;
    }

    public void deletar(Integer codigo) throws RegraDeNegocioException {
        VagaEntity vaga = findById(codigo);
        vagaRepository.delete(vaga);
    }

    public VagaEntity findById(Integer id) throws RegraDeNegocioException {
        return vagaRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada!"));
    }
}
