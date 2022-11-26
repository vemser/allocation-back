package br.com.allocation.dto.vagaDTO;

import br.com.allocation.entity.ProgramaEntity;
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
public class VagaCreateDTO {
    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome da vaga",example = "Desenvolvedor(a) Java - Back-End")
    private String nome;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Quantidade da vaga",example = "1")
    private Integer quantidade;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Situação da vaga",example = "ATIVO")
    private Situacao situacao;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Tipo de programa",example = "VemSer10")
    private ProgramaEntity programaEntity;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data abertura programa",example = "2022-12-20")
    private LocalDate dataAbertura;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data fechamento programa",example = "2022-12-26")
    private LocalDate dataFechamento;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Observaçoes sobre a vaga",example = "O que nós buscamos\n" +
            "Seguir conceitos de programação como: Alta coesão, Baixo acoplamento, e componentização.")
    private String observacoes;

    //private String nivel;
    //private String funcao;
}
