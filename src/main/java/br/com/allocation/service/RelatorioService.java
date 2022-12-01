package br.com.allocation.service;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
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
    public void enviarRelatiorio(){
        List<UsuarioDTO> usuarios = usuarioService.findAllUsers();
        List<VagaDTO> vagas = vagaService.findAllWithSituacaoAberto();
        List<AlunoDTO> alunos = alunoService.disponiveis();

        for(UsuarioDTO usuario:usuarios){
            emailService.sendEmail(vagas,usuario, alunos);
        }

    }
}
