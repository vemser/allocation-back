package br.com.allocation.dto.usuarioDTO;

import br.com.allocation.dto.cargoDTO.CargoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Integer idUsuario;
    private String nomeCompleto;
    private String email;
    private CargoDTO cargo;

}
