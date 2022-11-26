package br.com.allocation.dto.ClienteDTO;

import br.com.allocation.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreateDTO {
    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome do Cliente",example = "Sicred")
    private String nome;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "email",example = "")
    private String email;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Telefone do cliente",example = "911234-9876")
    private String telefone;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Situação ",example = "Ativo")
    private Situacao situacao;
}
