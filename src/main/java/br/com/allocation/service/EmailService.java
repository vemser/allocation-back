package br.com.allocation.service;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.entity.VagaEntity;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private static String TO = "kaio.andradre@dbccompany.com.br";

    private final JavaMailSender emailSender;
    private final UsuarioService usuarioService;
    private final VagaService vagaService;
    private final AlunoService alunoService;


    public void sendEmail(VagaDTO vagaDTO, UsuarioDTO usuario, AlunoDTO alunoDTO) {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuario.getNomeCompleto());
        dados.put("email", from);
        dados.put("aluno", alunoDTO.getNome());
        dados.put("vaga", vagaDTO.getNome());
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(TO);
            mimeMessageHelper.setTo(usuario.getEmail());
            mimeMessageHelper.setSubject("Resumo de vagas e alunos em aberto");
            mimeMessageHelper.setText(getContentFromTemplate(dados, "email-template.ftl"), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private String getContentFromTemplate(Map<String, Object> dados,
                                          String templateName) throws IOException, TemplateException {
        Template template = fmConfiguration.getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public void enviarRelatiorio(){
        List<UsuarioDTO> usuarios = usuarioService.findAllUsers();
        List<VagaDTO> vagas = vagaService.findAllWithSituacaoAberto();
        List<AlunoDTO> alunos = alunoService.disponiveis();
        AlunoDTO aluno = null;
        VagaDTO vaga = null;


        for(AlunoDTO alunoDTO: alunos){
            aluno = alunoDTO;
        }

        for(VagaDTO vagaDTO: vagas){
            vaga = vagaDTO;
        }

        for(UsuarioDTO usuario:usuarios){
            sendEmail(vaga,usuario, aluno);
        }

    }
}
