package br.com.allocation.dto.usuarioDTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer idUsuario;
    private String nomeCompleto;
    private String email;

}
