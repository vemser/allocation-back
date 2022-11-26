package br.com.allocation.dto.vagaDTO;

import br.com.allocation.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
}
