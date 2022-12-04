package br.com.allocation.service;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.cargoDTO.CargoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.factory.AlunoFactory;
import br.com.allocation.service.factory.VagaFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RelatorioServiceTest {

    @InjectMocks
    private RelatorioService relatorioService;

    @Mock
    private EmailService emailService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private VagaService vagaService;

    @Mock
    private AlunoService alunoService;


    @Test
    public void deveTestarEnviarRelatorioComSucesso() throws RegraDeNegocioException {
        PageDTO<UsuarioDTO> usuarioDTOPageDTO = new PageDTO<>();
        PageDTO<AlunoDTO> alunoDTOPageDTO = new PageDTO<>();

        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();
        List<VagaDTO> vagaDTOList = new ArrayList<>();
        List<AlunoDTO> alunoDTOList = new ArrayList<>();

        VagaDTO vaga = VagaFactory.getVagaDTO();
        UsuarioDTO usuario = getUsuarioDTO();
        alunoDTOPageDTO.setElementos(alunoDTOList);
        usuarioDTOPageDTO.setElementos(usuarioDTOList);
        AlunoDTO aluno = AlunoFactory.getAlunoDTO();

        usuarioDTOList.add(usuario);
        vagaDTOList.add(vaga);
        alunoDTOList.add(aluno);
        when(usuarioService.listarPorCargo(anyInt(), anyInt(), any())).thenReturn(usuarioDTOPageDTO);
        when(vagaService.findAllWithSituacaoAberto()).thenReturn(vagaDTOList);

        when(alunoService.listarDisponiveis(anyInt(), anyInt())).thenReturn(alunoDTOPageDTO);

        relatorioService.enviarRelatiorio();

        verify(emailService, times(1)).sendEmail(vagaDTOList, usuario, alunoDTOList);

    }

    private static UsuarioDTO getUsuarioDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(10);
        usuarioDTO.setNomeCompleto("gustavo lucena silva");
        usuarioDTO.setEmail("gustavo@dbccompany.com.br");

        CargoDTO cargoDTO = new CargoDTO();
        usuarioDTO.setCargo(cargoDTO);

        return usuarioDTO;
    }
}
