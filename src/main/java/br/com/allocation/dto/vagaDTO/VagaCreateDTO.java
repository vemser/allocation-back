package br.com.allocation.dto.vagaDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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

    @NotNull(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Quantidade da vaga",example = "1")
    private Integer quantidade;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Tipo de programa",example = "vemser 10")
    private String programa;

    @NotNull(message = "Data não pode ser vazio ou nulo.")
    @Schema(description = "Data abertura programa",example = "2022-12-20")
    private LocalDate dataAbertura;

    @NotNull(message = "Data não pode ser vazio ou nulo.")
    @Schema(description = "Data fechamento programa",example = "2022-12-26")
    private LocalDate dataFechamento;

    @NotNull(message = "Data não pode ser vazio ou nulo.")
    @Schema(description = "Data criação")
    private LocalDate dataCriacao;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Observaçoes sobre a vaga",example = "Seguir conceitos de programação como: Alta coesão, Baixo acoplamento, e componentização.")
    private String observacoes;

    @NotBlank(message = "Email não pode ser vazio ou nulo.")
    @Schema(description = "email do cliente",example = "sicred@dbccompany.com.br")
    @Email
    private String emailCliente;

    //private String nivel;
    //private String funcao;
}
