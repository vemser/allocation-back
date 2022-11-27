package br.com.allocation.dto.reservaAlocacaoDTO;

import br.com.allocation.entity.AvaliacaoEntity;
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
public class ReservaAlocacaoCreateDTO {
    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Codigo da reserva",example = "123")
    private Integer codigo;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Vaga",example = "Desenvolvedor(a) Java - Back-End")
    private String vaga;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Aluno",example = "Kaio")
    private String aluno;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Avaliação",example = "Avaliação1")
    private AvaliacaoEntity avaliacaoEntity;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Descrição",example = "O que nós buscamos\\n\" +\n" +
            "            \"Seguir conceitos de programação como: Alta coesão, Baixo acoplamento, e componentização.\"")
    private String descricao;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data reserva",example = "2022-12-26")
    private LocalDate dataReserva;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data reserva",example = "2023-12-26")
    private LocalDate dataAlocacao;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data reserva",example = "2023-12-26")
    private LocalDate dataCancelamento;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Situação Alocação Aluno",example = "Reservado")
    private Situacao situacao;
}
