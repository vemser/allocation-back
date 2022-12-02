package br.com.allocation.service;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.tecnologiaDTO.TecnologiaDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.entity.CargoEntity;
import br.com.allocation.entity.UsuarioEntity;
import br.com.allocation.enums.Area;
import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.StatusAluno;
import br.com.allocation.exceptions.RegraDeNegocioException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    final String from = "kaio@bol.com";

    @Mock
    private freemarker.template.Configuration fmConfiguration;

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private VagaService vagaService;
    @Mock
    private AlunoService alunoService;
    final String token = "t@12124r12edkqwdkqwdkqwdmqwdkqwoeqwe";
    @Mock
    private MimeMessage mimeMessage;
    @Mock
    private JavaMailSender emailSender;
    @Before
    public void init() {
        ReflectionTestUtils.setField(emailService, "from", from);
    }

    @Test
    public void deveTestarGetTemplateComSucesso() throws IOException, MessagingException, RegraDeNegocioException {
        //SETUP
        Template template = new Template("", Reader.nullReader());
        VagaDTO vagaDTO = getVagaDTO();
        AlunoDTO alunoDTO = getAlunoDTO();
        UsuarioDTO usuarioDTO = getUsuarioDTO();
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDTO.getNomeCompleto());
        dados.put("email", from);
        List<String> nomeVagas = new ArrayList<>();
        List<String> nomeAlunos = new ArrayList<>();
        String todasVagas = null;
        String todosAlunos = null;
        List<VagaDTO> vagaDTOS = new ArrayList<>();
        List<AlunoDTO> alunoDTOS = new ArrayList<>();
        vagaDTOS.add(vagaDTO);
        alunoDTOS.add(alunoDTO);
        nomeAlunos.add(alunoDTOS.get(0).getNome());
        nomeVagas.add(vagaDTOS.get(0).getNome());
        todasVagas = nomeVagas.toString();
        todosAlunos = nomeAlunos.toString();
        dados.put("vaga", todasVagas);
        dados.put("aluno", todosAlunos);
        mimeMessage.setFrom(from);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(fmConfiguration.getTemplate(any())).thenReturn(template);


        //ACT
        emailService.sendEmail(vagaDTOS, usuarioDTO, alunoDTOS);

        //ASSERT
        verify(emailSender).send((MimeMessage) any());

    }

    @Test
    public void deveTestarGetTemplateComSucessoNenhumaVaga() throws IOException, MessagingException, RegraDeNegocioException {
        //SETUP
        Template template = new Template("", Reader.nullReader());
        VagaDTO vagaDTO = getVagaDTO();
        AlunoDTO alunoDTO = getAlunoDTO();
        UsuarioDTO usuarioDTO = getUsuarioDTO();
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDTO.getNomeCompleto());
        dados.put("email", from);
        List<String> nomeVagas = new ArrayList<>();
        List<String> nomeAlunos = new ArrayList<>();
        String todasVagas = null;
        String todosAlunos = null;
        List<VagaDTO> vagaDTOS = new ArrayList<>();
        List<AlunoDTO> alunoDTOS = new ArrayList<>();
        alunoDTOS.add(alunoDTO);
        nomeAlunos.add(alunoDTOS.get(0).getNome());
        todasVagas = nomeVagas.toString();
        dados.put("vaga", todasVagas);
        dados.put("aluno", todosAlunos);
        mimeMessage.setFrom(from);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(fmConfiguration.getTemplate(any())).thenReturn(template);


        //ACT
        emailService.sendEmail(vagaDTOS, usuarioDTO, alunoDTOS);

        //ASSERT
        verify(emailSender).send((MimeMessage) any());

    }

    @Test
    public void deveTestarGetTemplateComSucessoNenhumAluno() throws IOException, MessagingException, RegraDeNegocioException {
        //SETUP
        Template template = new Template("", Reader.nullReader());
        VagaDTO vagaDTO = getVagaDTO();
        AlunoDTO alunoDTO = getAlunoDTO();
        UsuarioDTO usuarioDTO = getUsuarioDTO();
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDTO.getNomeCompleto());
        dados.put("email", from);
        List<String> nomeVagas = new ArrayList<>();
        List<String> nomeAlunos = new ArrayList<>();
        String todasVagas = null;
        String todosAlunos = null;
        List<VagaDTO> vagaDTOS = new ArrayList<>();
        List<AlunoDTO> alunoDTOS = new ArrayList<>();
        vagaDTOS.add(vagaDTO);
        nomeVagas.add(vagaDTOS.get(0).getNome());
        todasVagas = nomeVagas.toString();
        todosAlunos = nomeAlunos.toString();
        dados.put("vaga", todasVagas);
        dados.put("aluno", todosAlunos);
        mimeMessage.setFrom(from);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(fmConfiguration.getTemplate(any())).thenReturn(template);


        //ACT
        emailService.sendEmail(vagaDTOS, usuarioDTO, alunoDTOS);

        //ASSERT
        verify(emailSender).send((MimeMessage) any());

    }

    @Test
    public void deveTestarGetTemplateComErro() throws IOException, MessagingException, RegraDeNegocioException {
        //SETUP
        Template template = new Template("", Reader.nullReader());
        VagaDTO vagaDTO = getVagaDTO();
        AlunoDTO alunoDTO = getAlunoDTO();
        UsuarioDTO usuarioDTO = getUsuarioDTO();
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDTO.getNomeCompleto());
        dados.put("email", from);
        List<String> nomeVagas = new ArrayList<>();
        List<String> nomeAlunos = new ArrayList<>();
        String todasVagas = null;
        String todosAlunos = null;
        List<VagaDTO> vagaDTOS = new ArrayList<>();
        List<AlunoDTO> alunoDTOS = new ArrayList<>();
        vagaDTOS.add(vagaDTO);
        alunoDTOS.add(alunoDTO);
        nomeAlunos.add(alunoDTOS.get(0).getNome());
        nomeVagas.add(vagaDTOS.get(0).getNome());
        todasVagas = nomeVagas.toString();
        todosAlunos = nomeAlunos.toString();
        dados.put("vaga", todasVagas);
        dados.put("aluno", todosAlunos);
        mimeMessage.setFrom(from);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);


        //ACT
        doThrow(new IOException()).when(fmConfiguration).getTemplate(anyString());
        emailService.sendEmail(vagaDTOS, usuarioDTO, alunoDTOS);

    }

    @Test
    public void deveTestarSendEmailRecuperarSenhaComSucesso() throws IOException {
        Template template = new Template("template.html", Reader.nullReader());
        UsuarioEntity usuario = getUsuarioEntity();

        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        doThrow(new IOException()).when(fmConfiguration).getTemplate(anyString());
        emailService.sendEmailRecuperarSenha(usuario, token);

    }

    @Test
    public void deveTestarSendEmailRecuperarSenhaComErro() throws IOException {
        Template template = new Template("template.html", Reader.nullReader());
        UsuarioEntity usuario = getUsuarioEntity();

        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendEmailRecuperarSenha(usuario, token);

        verify(emailSender).send((MimeMessage) any());

    }
    

    private static UsuarioDTO getUsuarioDTO(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(1);
        usuarioDTO.setEmail("Kaio@gmail.com");
        usuarioDTO.setNomeCompleto("Kaio Antonio");
        return  usuarioDTO;
    }

    private static VagaDTO getVagaDTO(){
        VagaDTO vagaDTO = new VagaDTO();
        vagaDTO.setIdVaga(1);
        vagaDTO.setSituacao(Situacao.valueOf(String.valueOf(Situacao.ATIVO)));
        vagaDTO.setDataAbertura(LocalDate.now());
        vagaDTO.setDataCriacao(LocalDate.now());
        vagaDTO.setDataFechamento(LocalDate.of(2023,01,01));
        vagaDTO.setQuantidade(1);
        vagaDTO.setQuantidadeAlocados(0);
        return vagaDTO;
    }

    private static AlunoDTO getAlunoDTO(){
        Set<TecnologiaDTO> tecnologia = new HashSet<>();
        TecnologiaDTO tecnologiaDTO = new TecnologiaDTO();
        tecnologiaDTO.setNome("Java");
        tecnologia.add(tecnologiaDTO);
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setIdAluno(1);
        alunoDTO.setEmail("kaio@mail.com");
        alunoDTO.setStatusAluno(StatusAluno.DISPONIVEL);
        alunoDTO.setArea(Area.BACKEND);
        return alunoDTO;
    }

    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNomeCompleto("gustavo lucena silva");
        usuarioEntity.setEmail("gustavo@dbccompany.com.br");
        usuarioEntity.setSenha("uiq@123456");
        usuarioEntity.setIdUsuario(10);

        CargoEntity cargoEntity = new CargoEntity(1,"ROLE_ADMINISTRADOR", new HashSet<>());
        usuarioEntity.getCargos().add(cargoEntity);

        return usuarioEntity;
    }

}
