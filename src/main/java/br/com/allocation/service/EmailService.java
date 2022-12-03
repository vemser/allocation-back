package br.com.allocation.service;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.entity.UsuarioEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {
    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    public void sendEmail(List<VagaDTO> vagaDTO, UsuarioDTO usuario, List<AlunoDTO> alunoDTO) throws RegraDeNegocioException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuario.getNomeCompleto());
        dados.put("email", from);
        List<String> nomeVagas = new ArrayList<>();
        List<String> nomeAlunos = new ArrayList<>();
        String todasVagas = null;
        String todosAlunos = null;
        if (vagaDTO.size() == 0) {
            todasVagas = "Nenhuma vaga em aberto!";
        }
        if (alunoDTO.size() == 0) {
            todosAlunos = "Nenhuma aluno disponível!";
        }
        for (VagaDTO vagas : vagaDTO) {
            nomeVagas.add(vagas.getNome());
            todasVagas = nomeVagas.toString();
        }
        dados.put("vaga", todasVagas);
        for (AlunoDTO aluno : alunoDTO) {
            nomeAlunos.add(aluno.getNome());
            todosAlunos = nomeAlunos.toString();

        }
        dados.put("aluno", todosAlunos);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuario.getEmail());
            mimeMessageHelper.setSubject("Resumo de vagas e alunos em aberto");
            mimeMessageHelper.setText(getContentFromTemplate(dados, "email-template.html"), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailRecuperarSenha(UsuarioEntity usuarioEntity, String token) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuarioEntity.getEmail());
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(geContentFromRecuperarSenha(usuarioEntity, token), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplate(Map<String, Object> dados,
                                         String templateName) throws IOException, TemplateException {
        Template template = fmConfiguration.getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public String geContentFromRecuperarSenha(UsuarioEntity usuarioEntity, String token) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        Template template = null;

        String link = "versel/atualizar-senha?token=" + token;

        dados.put("nome", usuarioEntity.getNomeCompleto());
        dados.put("email", from);
        dados.put("link", link);

        template = fmConfiguration.getTemplate("email-recuperar-senha-template.html");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }
}
