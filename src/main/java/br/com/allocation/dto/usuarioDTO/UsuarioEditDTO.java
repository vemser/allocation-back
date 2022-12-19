package br.com.allocation.dto.usuariodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UsuarioEditDTO {

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome completo do usuario", example = "Jhennyfer Silva Sobrinho")
    private String nomeCompleto;

    @Email
    @NotBlank(message = "email não pode ser vazio ou nulo.")
    @Schema(description = "email do usuario", example = "jhennyfer.sobrinho@dbccompany.com.br")
    private String email;


}
