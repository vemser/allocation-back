package br.com.allocation.dto.reservaAlocacaoDTO;

import br.com.allocation.enums.StatusAluno;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaAlocacaoCreateDTO {


    //@NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "id da Vaga", example = "24")
    private Integer idVaga;

    //@NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "id do Aluno", example = "8")
    private Integer idAluno;

    //@NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "id da avaliaçao", example = "5")
    private Integer idAvaliacao;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Descrição", example = "O que nós buscamos " +
            "Seguir conceitos de programação como: Alta coesão, Baixo acoplamento, e componentização.")
    private String descricao;

    //@NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data reserva,alocação ou cancelamento", example = "2022-12-26")
    private LocalDate data;
//
//    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
//    @Schema(description = "Data reserva",example = "2023-12-26")
//    private LocalDate dataAlocacao;
//
//    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
//    @Schema(description = "Data reserva",example = "2023-12-26")
//    private LocalDate dataCancelamento;

    //@NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Status Alocação Aluno", example = "RESERVADO")
    private StatusAluno statusAluno;
}
