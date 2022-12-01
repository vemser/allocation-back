package br.com.allocation.dto.avaliacaoDTO;

import br.com.allocation.enums.SituacaoAluno;
import br.com.allocation.enums.TipoAvaliacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoCreateDTO {

    @NotNull(message = "codigoVaga não pode ser nulo.")
    @Schema(description = "Codigo da vaga",example = "1")
    private Integer idVaga;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome do aluno",example = "jhennyfer.sobrinho@dbccompany.com.br")
    private String emailAluno;

    @NotNull(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nota do aluno",example = "10")
    private Integer nota;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "descrição d avaliação do aluno",example = "Muito bom")
    private String descricao;

    @NotNull(message = "Data não pode ser vazio ou nulo.")
    @Schema(description = "Data da avaliação",example = "2022-12-24")
    private LocalDate dataAvaliacao;

    @NotNull(message = "Data não pode ser vazio ou nulo.")
    @Schema(description = "Data da entrevista",example = "2022-12-20")
    private LocalDate dataEntrevista;

    @NotNull(message = "Data não pode ser vazio ou nulo.")
    @Schema(description = "Data da resposta",example = "2022-12-22")
    private LocalDate dataResposta;

    @NotNull(message = "Data não pode ser vazio ou nulo.")
    @Schema(description = "Data criacao")
    private LocalDate dataCriacao;

    @NotNull(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "tipo Avaliacao",example = "INDIVIDUAL")
    private TipoAvaliacao tipoAvaliacao;

    @NotNull(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "situação",example = "AVALIACAO1")
    private String situacao;


}
