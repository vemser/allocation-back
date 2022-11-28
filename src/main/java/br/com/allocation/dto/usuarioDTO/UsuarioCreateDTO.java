package br.com.allocation.dto.usuarioDTO;

import br.com.allocation.dto.cargoDTO.CargoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome completo do usuario",example = "Jhennyfer Silva Sobrinho")
    private String nomeCompleto;

    @Email
    @NotBlank(message = "email não pode ser vazio ou nulo.")
    @Schema(description = "email do usuario",example = "jhennyfer.sobrinho@dbccompany.com.br")
    private String email;

    @NotBlank(message = "senha não pode ser vazio ou nulo.")
    @Schema(description = "senha do usuario ",example = "pqasde12@")
    @Size(min = 8, max = 25)
    private String senha;

    @NotBlank(message = "senha não pode ser vazio ou nulo.")
    @Schema(description = "confirmação de senha ",example = "pqasde12@")
    @Size(min = 8, max = 25)
    private String senhaIgual;

    @NotNull
    private CargoDTO cargo;

}
