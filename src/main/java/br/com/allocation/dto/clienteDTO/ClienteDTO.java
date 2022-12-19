package br.com.allocation.dto.clientedto;

import br.com.allocation.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClienteDTO {
    private Integer idCliente;
    private String nome;
    private String email;
    private String telefone;
    private Situacao situacao;

}
