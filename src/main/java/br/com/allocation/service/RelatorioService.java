package br.com.allocation.service;

import br.com.allocation.dto.alunodto.AlunoDTO;
import br.com.allocation.dto.usuariodto.UsuarioDTO;
import br.com.allocation.dto.vagadto.VagaDTO;
import br.com.allocation.enums.Cargos;
import br.com.allocation.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RelatorioService {

    private final EmailService emailService;
    private final UsuarioService usuarioService;
    private final VagaService vagaService;
    private final AlunoService alunoService;


    @Scheduled(cron = "0 0 * * * MON")
    public void enviarRelatiorio() throws RegraDeNegocioException {
        List<UsuarioDTO> usuarios = usuarioService.listarPorCargo(0, 20, Cargos.GESTOR).getElementos();
        List<VagaDTO> vagas = vagaService.findAllWithSituacaoAberto();
        List<AlunoDTO> alunos = alunoService.listarDisponiveis(0, 20).getElementos().stream().toList();

        for (UsuarioDTO usuario : usuarios) {
            emailService.sendEmail(vagas, usuario, alunos);
        }
    }
}
