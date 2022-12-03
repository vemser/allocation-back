package br.com.allocation.dto.usuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioSenhaDTO {
    private String senha;
    private String senhaIgual;
}
