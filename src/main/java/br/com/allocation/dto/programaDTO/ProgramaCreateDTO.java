package br.com.allocation.dto.programaDTO;

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
public class ProgramaCreateDTO {
    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Nome do programa",example = "VemSer 10ed")
    private String nome;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Descrição do programa",example = "Programa de formação profissional trilha Backend Vem Ser DBC 10º edição.")
    private String descricao;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Data de abertura programa",example = "2023-02-23")
    private LocalDate dataCriacao;

    @NotBlank(message = "Nome não pode ser vazio ou nulo.")
    @Schema(description = "Situação do programa",example = "ABERTO")
    private Situacao situacao;
}
