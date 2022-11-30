//package br.com.allocation.service;
//
//import br.com.allocation.dto.alunoDTO.AlunoDTO;
//import br.com.allocation.dto.tecnologiaDTO.TecnologiaDTO;
//import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
//import br.com.allocation.dto.vagaDTO.VagaDTO;
//import br.com.allocation.enums.Area;
//import br.com.allocation.enums.Situacao;
//import br.com.allocation.enums.StatusAluno;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.io.Reader;
//import java.time.LocalDate;
//import java.util.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class EmailServiceTest {
//
//    @InjectMocks
//    private EmailService emailService;
//
//    @Value("${spring.mail.username}")
//    private String from;
//
//    @Mock
//    private freemarker.template.Configuration fmConfiguration;
//
//    @Mock
//    private UsuarioService usuarioService;
//    @Mock
//    private VagaService vagaService;
//    @Mock
//    private AlunoService alunoService;
//
//
////    @Test
////    public void deveTestarGetTemplateComSucesso() throws IOException, MessagingException {
////        //SETUP
////        Template template = new Template("", Reader.nullReader());
////        VagaDTO vagaDTO = getVagaDTO();
////        AlunoDTO alunoDTO = getAlunoDTO();
////        UsuarioDTO usuarioDTO = getUsuarioDTO();
////        Map<String, Object> dados = new HashMap<>();
////        dados.put("nome", usuarioDTO.getNomeCompleto());
////        dados.put("email", from);
////        List<String> nomeVagas = new ArrayList<>();
////        List<String> nomeAlunos = new ArrayList<>();
////        String todasVagas = null;
////        String todosAlunos = null;
////        List<VagaDTO> vagaDTOS = new ArrayList<>();
////        List<AlunoDTO> alunoDTOS = new ArrayList<>();
////        vagaDTOS.add(vagaDTO);
////        alunoDTOS.add(alunoDTO);
////        nomeAlunos.add(alunoDTOS.get(0).getNome());
////        nomeVagas.add(vagaDTOS.get(0).getNome());
////        todasVagas = nomeVagas.toString();
////        todosAlunos = nomeAlunos.toString();
////        dados.put("vaga", todasVagas);
////        dados.put("aluno", todosAlunos);
////        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
////        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
////        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
////        mimeMessageHelper.setFrom("Kaio@bol.com");
////        mimeMessageHelper.setTo(usuarioDTO.getEmail());
////        mimeMessageHelper.setSubject("Resumo de vagas e alunos em aberto");
////        mimeMessageHelper.setText("email-template.html");
////
////        //ACT
////        emailService.sendEmail(vagaDTOS, usuarioDTO, alunoDTOS);
////
////        //ASSERT
////        verify(emailService, times(1)).sendEmail(anyList(),any(),anyList());
////
////    }
//
//    @Test
//    public void deveTestarGetContentFromTemplateComSucesso() throws IOException, TemplateException {
//        Template template = new Template("", Reader.nullReader());
//        VagaDTO vagaDTO = getVagaDTO();
//        AlunoDTO alunoDTO = getAlunoDTO();
//        UsuarioDTO usuarioDTO = getUsuarioDTO();
//        Map<String, Object> dados = new HashMap<>();
//        dados.put("nome", usuarioDTO.getNomeCompleto());
//        dados.put("email", from);
//        List<String> nomeVagas = new ArrayList<>();
//        List<String> nomeAlunos = new ArrayList<>();
//        String todasVagas = null;
//        String todosAlunos = null;
//        List<VagaDTO> vagaDTOS = new ArrayList<>();
//        List<AlunoDTO> alunoDTOS = new ArrayList<>();
//        vagaDTOS.add(vagaDTO);
//        alunoDTOS.add(alunoDTO);
//        nomeAlunos.add(alunoDTOS.get(0).getNome());
//        nomeVagas.add(vagaDTOS.get(0).getNome());
//        todasVagas = nomeVagas.toString();
//        todosAlunos = nomeAlunos.toString();
//        dados.put("vaga", todasVagas);
//        dados.put("aluno", todosAlunos);
//        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);
//
//        //ACT
//        String getContent = emailService.getContentFromTemplate(dados, String.valueOf(template));
//        template = fmConfiguration.getTemplate("email-template.html");
//        FreeMarkerTemplateUtils.processTemplateIntoString(template,dados);
//
//        //ASSERT
//        assertNotNull(getContent);
//
//    }
//
//    @Test
//    public void deveTestarEnviarRelatorioComSucesso(){
//        //SETUP
//        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
//        List<VagaDTO> vagaDTOS = new ArrayList<>();
//        List<AlunoDTO> alunoDTOS = new ArrayList<>();
//
//        UsuarioDTO usuarioDTO = getUsuarioDTO();
//        VagaDTO vagaDTO = getVagaDTO();
//        AlunoDTO alunoDTO = getAlunoDTO();
//
//        usuarioDTOS.add(usuarioDTO);
//        vagaDTOS.add(vagaDTO);
//        alunoDTOS.add(alunoDTO);
//
//        when(usuarioService.findAllUsers()).thenReturn(usuarioDTOS);
//        when(vagaService.findAllWithSituacaoAberto()).thenReturn(vagaDTOS);
//        when(alunoService.disponiveis()).thenReturn(alunoDTOS);
//        when(emailService.sendEmail(vagaDTOS, usuarioDTO, alunoDTOS));
//
//        //ACT
//        emailService.enviarRelatiorio();
//
//        //ASSERT
//        verify(emailService, times(1)).enviarRelatiorio();
//
//
//    }
//
//    private static UsuarioDTO getUsuarioDTO(){
//        UsuarioDTO usuarioDTO = new UsuarioDTO();
//        usuarioDTO.setIdUsuario(1);
//        usuarioDTO.setEmail("Kaio@gmail.com");
//        usuarioDTO.setNomeCompleto("Kaio Antonio");
//        return  usuarioDTO;
//    }
//
//    private static VagaDTO getVagaDTO(){
//        VagaDTO vagaDTO = new VagaDTO();
//        vagaDTO.setCodigo(1);
//        vagaDTO.setSituacao(String.valueOf(Situacao.ATIVO));
//        vagaDTO.setEmailCliente("Cocacola@gmail.com");
//        vagaDTO.setDataAbertura(LocalDate.now());
//        vagaDTO.setDataCriacao(LocalDate.now());
//        vagaDTO.setDataFechamento(LocalDate.of(2023,01,01));
//        vagaDTO.setObservacoes("Bem observado");
//        vagaDTO.setQuantidade(1);
//        vagaDTO.setQuantidadeAlocados(0);
//        return vagaDTO;
//    }
//
//    private static AlunoDTO getAlunoDTO(){
//        Set<TecnologiaDTO> tecnologia = new HashSet<>();
//        TecnologiaDTO tecnologiaDTO = new TecnologiaDTO();
//        tecnologiaDTO.setNome("Java");
//        tecnologia.add(tecnologiaDTO);
//        AlunoDTO alunoDTO = new AlunoDTO();
//        alunoDTO.setIdAluno(1);
//        alunoDTO.setEmail("kaio@mail.com");
//        alunoDTO.setStatusAluno(StatusAluno.DISPONIVEL);
//        alunoDTO.setArea(Area.BACKEND);
//        alunoDTO.setTecnologias(tecnologia);
//        return alunoDTO;
//    }
//
//}
