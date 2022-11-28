package br.com.allocation.dto.clienteDTO;

import br.com.allocation.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ClienteCreateDTO {
    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome do Cliente",example = "Sicred")
    private String nome;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "email",example = "")
    @Email
    private String email;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Telefone do cliente",example = "911234-9876")
    private String telefone;

}
