package br.com.allocation.service;

import br.com.allocation.dto.ClienteDTO.ClienteCreateDTO;
import br.com.allocation.dto.ClienteDTO.ClienteDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.entity.ClienteEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {


    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;


    public ClienteDTO salvar(ClienteCreateDTO clienteCreate) {
        ClienteEntity clienteEntity = objectMapper.convertValue(clienteCreate, ClienteEntity.class);

        return objectMapper.convertValue(clienteRepository.save(clienteEntity), ClienteDTO.class);
    }

    public PageDTO<ClienteDTO> listar(Integer pagina, Integer tamanho){
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ClienteEntity> paginaRepository = clienteRepository.findAll(pageRequest);

        List<ClienteDTO> ClientePagina = paginaRepository.getContent().stream()
                .map(x -> objectMapper.convertValue(x, ClienteDTO.class))
                .toList();

        return new PageDTO<>(paginaRepository.getTotalElements(), paginaRepository.getTotalPages(), pagina, tamanho, ClientePagina);
    }

    public ClienteDTO editar(Integer idCliente, ClienteCreateDTO clienteCreate) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = findById(idCliente);

        ClienteEntity clienteAtualizar = objectMapper.convertValue(clienteCreate, ClienteEntity.class);
        clienteAtualizar.setIdCliente(idCliente);

        clienteAtualizar = clienteRepository.save(clienteAtualizar);
        return objectMapper.convertValue(clienteAtualizar, ClienteDTO.class);
    }

    public void deletar(Integer idCliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = findById(idCliente);
        clienteRepository.delete(clienteEntity);
    }

    public ClienteEntity findById(Integer id) throws RegraDeNegocioException {
        return clienteRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }
}
