package br.com.allocation.dto.tecnologiaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TecnologiaDTO extends TecnologiaCreateDTO {
    private Integer idTecnologia;
}
