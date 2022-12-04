package br.com.allocation.dto.vagaDTO;

import br.com.allocation.dto.clienteDTO.ClienteDTO;
import br.com.allocation.enums.SituacaoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaDTO {

    private Integer idVaga;
    private String nome;
    private Integer quantidade;
    private Integer quantidadeAlocados;
    private Integer idPrograma;
    private SituacaoCliente situacaoCliente;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private LocalDate dataCriacao;
    private ClienteDTO clienteDTO;

}
