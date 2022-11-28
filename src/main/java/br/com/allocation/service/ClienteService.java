package br.com.allocation.service;

import br.com.allocation.dto.clienteDTO.ClienteCreateDTO;
import br.com.allocation.dto.clienteDTO.ClienteDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.entity.ClienteEntity;
import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.SituacaoCliente;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Client;
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


    public ClienteDTO salvar(ClienteCreateDTO clienteCreate, SituacaoCliente situacao) {
        ClienteEntity clienteEntity = converterEntity(clienteCreate);
        clienteEntity.setSituacaoCliente(situacao);
        return converterEmDTO(clienteRepository.save(clienteEntity));
    }

    public PageDTO<ClienteDTO> listar(Integer pagina, Integer tamanho){
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ClienteEntity> paginaRepository = clienteRepository.findAll(pageRequest);

        List<ClienteDTO> clienteDTOList = paginaRepository.getContent().stream()
                .map(this::converterEmDTO)
                .toList();

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                clienteDTOList);
    }

    public ClienteDTO editar(Integer idCliente, ClienteCreateDTO clienteCreate, SituacaoCliente situacao) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = findById(idCliente);
        clienteEntity.setNome(clienteCreate.getNome());
        clienteEntity.setEmail(clienteEntity.getEmail());
        clienteEntity.setTelefone(clienteEntity.getTelefone());
        clienteEntity.setSituacaoCliente(situacao);
        clienteEntity = clienteRepository.save(clienteEntity);
        return converterEmDTO(clienteEntity);
    }

    public void deletar(Integer idCliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = findById(idCliente);
        clienteRepository.delete(clienteEntity);
    }

    public ClienteEntity findById(Integer id) throws RegraDeNegocioException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public ClienteEntity findByEmail(String email){
        return clienteRepository.findByEmail(email);
    }

    private ClienteEntity converterEntity(ClienteCreateDTO clienteCreateDTO) {
        return objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
    }
    private ClienteDTO converterEmDTO(ClienteEntity clienteEntity) {
        return objectMapper.convertValue(clienteEntity, ClienteDTO.class);
    }
}
