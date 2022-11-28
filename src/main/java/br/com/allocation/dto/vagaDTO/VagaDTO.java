package br.com.allocation.dto.vagaDTO;

import br.com.allocation.dto.programaDTO.ProgramaDTO;
import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaDTO extends  VagaCreateDTO{
    private Integer id;

}
