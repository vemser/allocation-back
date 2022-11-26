package br.com.allocation.dto.AlunoDTO;

import br.com.allocation.enums.Area;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlunoCreate {

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome do aluno",example = "Gustavo Lucena")
    private String nome;

    @Email
    @NotBlank(message = "email não pode ser vazio ou nulo.")
    @Schema(description = "email do usuario",example = "jhennyfer.sobrinho@dbccompany.com.br")
    private String email;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome da area ",example = "Front-end")
    private Area area;

    private String cidade;

    private String estado;

    private String telefone;


    private String descricao;
}
