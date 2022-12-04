package br.com.allocation.dto.clienteDTO;

import br.com.allocation.enums.SituacaoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClienteDTO {
    private Integer idCliente;
    private String nome;
    private String email;
    private String telefone;
    private SituacaoCliente situacaoCliente;

}
