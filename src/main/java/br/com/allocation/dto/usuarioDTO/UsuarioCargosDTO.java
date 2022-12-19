package br.com.allocation.dto.usuariodto;

import br.com.allocation.dto.cargodto.CargoDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UsuarioCargosDTO {
    @NotNull
    private String emailUsuario;
    @NotNull
    private CargoDTO cargo;
}
