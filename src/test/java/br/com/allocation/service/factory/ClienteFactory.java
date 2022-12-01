package br.com.allocation.service.factory;

import br.com.allocation.dto.clienteDTO.ClienteCreateDTO;
import br.com.allocation.entity.ClienteEntity;
import br.com.allocation.enums.Situacao;

public class ClienteFactory {
    public static ClienteEntity getClienteEntity(){
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setIdCliente(1);
        clienteEntity.setNome("Coca Cola");
        clienteEntity.setTelefone("711112459798");
        clienteEntity.setEmail("cocacolabr@mail.com.br");
        clienteEntity.setSituacao(Situacao.ATIVO);
        return clienteEntity;
    }

    public static ClienteCreateDTO getClienteCreateDTO(){
        ClienteCreateDTO clienteCreateDTO = new ClienteCreateDTO();
        clienteCreateDTO.setNome("Coca Cola");
        clienteCreateDTO.setTelefone("711112459798");
        clienteCreateDTO.setEmail("cocacolabr@mail.com.br");
        clienteCreateDTO.setSituacao(Situacao.valueOf(String.valueOf(Situacao.ATIVO)));
        return clienteCreateDTO;
    }
}
