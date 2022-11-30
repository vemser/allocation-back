package br.com.allocation.dto.usuarioDTO;

import br.com.allocation.dto.cargoDTO.CargoDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UsuarioCargosDTO {
    @NotNull
    private String emailUsuario;
    @NotNull
    private CargoDTO cargo;
}
