package br.com.allocation.dto.usuariodto;

import br.com.allocation.dto.cargodto.CargoDTO;
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
