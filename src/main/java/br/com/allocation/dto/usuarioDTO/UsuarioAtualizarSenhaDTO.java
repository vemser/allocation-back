package br.com.allocation.dto.usuariodto;

import lombok.Data;

@Data
public class UsuarioAtualizarSenhaDTO {

    private String senhaAntiga;
    private String senha;
    private String senhaIgual;
}
