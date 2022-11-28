package br.com.allocation.dto.alunoDTO;

import br.com.allocation.enums.Area;
import br.com.allocation.enums.StatusAluno;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoCreateDTO {

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome do aluno", example = "Gustavo Lucena")
    private String nome;

    //@Email
    @NotBlank(message = "email não pode ser vazio ou nulo.")
    @Schema(description = "email do usuario", example = "jhennyfer.sobrinho@dbccompany.com.br")
    private String email;

    @NotBlank(message = "não pode ser vazio ou nulo.")
    @Schema(description = "Programa", example = "vemser 10")
    private String programa;

    @Schema(description = "Nome da area ", example = "FRONT")
    private Area area;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Cidade do aluno ", example = "Pato Branco")
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

    @NotNull(message = "Nome não pode ser vazio ou nulo.")
    private StatusAluno statusAluno;

    @Schema(description = "tecnologias")
    private List<String> tecnologias = new ArrayList<>();
}
