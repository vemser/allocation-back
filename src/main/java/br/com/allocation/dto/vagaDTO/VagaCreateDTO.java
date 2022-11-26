package br.com.allocation.dto.vagaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VagaCreateDTO {
    private String nome;
    private Integer quantidade;
    private Integer quantidadeAlocados;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private LocalDate dataCriacao;
    private String situacao;

}
