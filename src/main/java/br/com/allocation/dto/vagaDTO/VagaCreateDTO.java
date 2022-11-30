package br.com.allocation.dto.vagaDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VagaCreateDTO {

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome da vaga",example = "Desenvolvedor(a) Java - Back-End")
    private String nome;

    @NotNull(message = "Quantidade não pode ser vazio ou nulo.")
    @Schema(description = "Quantidade de pessoas para a vaga",example = "2")
    private Integer quantidade;

    @NotNull(message = "Quantidade não pode ser vazio ou nulo.")
    @Schema(description = "Quantidade de pessoas alocadas na vaga",example = "1")
    private Integer quantidadeAlocados;

    @NotNull(message = "Id programa não pode ser nulo.")
    @Schema(description = "Id do programa",example = "1")
    private Integer idPrograma;

    @NotNull(message = "situacao não pode ser nulo.")
    @Schema(description = "situacao da vaga",example = "ABERTO")
    private String situacao;

    @NotNull(message = "Data não pode ser vazio ou nulo.")
    @Schema(description = "Data abertura vaga",example = "2022-12-20")
    private LocalDate dataAbertura;

    @NotNull(message = "Data não pode ser vazio ou nulo.")
    @Schema(description = "Data fechamento vaga",example = "2022-12-26")
    private LocalDate dataFechamento;

    @NotNull(message = "Data não pode ser vazio ou nulo.")
    @Schema(description = "Data criação")
    private LocalDate dataCriacao;

    @NotBlank(message = "observacoes não pode ser vazio ou nulo.")
    @Schema(description = "Observaçoes sobre a vaga",example = "Seguir conceitos de programação como: Alta coesão, Baixo acoplamento, e componentização.")
    private String observacoes;

    @NotBlank(message = "Email não pode ser vazio ou nulo.")
    @Schema(description = "email do cliente",example = "sicred@dbccompany.com.br")
    @Email
    private String emailCliente;

}
