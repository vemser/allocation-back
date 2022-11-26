package br.com.allocation.service;

import br.com.allocation.dto.AlunoDTO.AlunoCreateDTO;
import br.com.allocation.dto.AlunoDTO.AlunoDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final ObjectMapper objectMapper;

    public AlunoDTO create(AlunoCreateDTO alunoCreate){
        AlunoEntity alunoEntity = converterEntity(alunoCreate);
        AlunoDTO alunoDTO = converterEmDTO(alunoEntity);
        return alunoDTO;
    }
    private AlunoEntity converterEntity(AlunoCreateDTO alunoCreateDTO) {
        return objectMapper.convertValue(alunoCreateDTO, AlunoEntity.class);
    }
    private AlunoDTO converterEmDTO(AlunoEntity alunoEntity) {
//       AlunoDTO alunoDTO = new AlunoDTO(alunoEntity.getNome(),
//               alunoEntity.getArea(), alunoEntity.getEmail(),alunoEntity.ge)
        return objectMapper.convertValue(alunoEntity, AlunoDTO.class);
    }
}
