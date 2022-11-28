package br.com.allocation.dto.avaliacaoDTO;

import br.com.allocation.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoCreateDTO {
    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Codigo da avaliação",example = "123")
    private Integer codigo;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome da vaga",example = "Desenvolvedor(a) Java - Back-End")
    private String vaga;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome do aluno",example = "Jhennyfer Sobrinho")
    private String aluno;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nota do aluno",example = "10")
    private Integer nota;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "descrição d avaliação do aluno",example = "Muito bom")
    private String descricao;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data da entrevista com gestão de pessoas ",example = "2022-12-19")
    private LocalDate dataEntrevistaGP;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data da avaliação",example = "2022-12-24")
    private LocalDate dataAvaliacao;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data da entrevista",example = "2022-12-20")
    private LocalDate dataEntrevista;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data da resposta",example = "2022-12-22")
    private LocalDate dataResposta;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "situação",example = "avaliado")
    private Situacao situacao;


}
