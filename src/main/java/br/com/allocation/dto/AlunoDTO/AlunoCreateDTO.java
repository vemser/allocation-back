package br.com.allocation.dto.AlunoDTO;

import br.com.allocation.enums.Area;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoCreateDTO {

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome do aluno",example = "Gustavo Lucena")
    private String nome;

    @Email
    @NotBlank(message = "email não pode ser vazio ou nulo.")
    @Schema(description = "email do usuario",example = "gustavo.lucena@dbccompany.com.br")
    private String email;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome da area ",example = "Front-end")
    private Area area;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Cidade do aluno ",example = "Pato Branco")
    private String cidade;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Estado do aluno", example = "Paraná")
    private String estado;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Telefone do contato", example = "99595-1313")
    private String telefone;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "descrição", example = "xxxx")
    private String descricao;
}
