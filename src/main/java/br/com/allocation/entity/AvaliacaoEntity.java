package br.com.allocation.entity;

import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.TipoAvaliacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Avaliacao")
public class AvaliacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AVALIACAO")
    @SequenceGenerator(name = "SEQ_AVALIACAO", sequenceName = "seq_avaliacao", allocationSize = 1)
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "id_aluno")
    private Integer idAluno;

    @Column(name = "codigo_vaga")
    private Integer codigoVaga;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "tipo_avaliacao")
    private TipoAvaliacao tipoAvaliacao;

    @Column(name = "data_avaliacao")
    private LocalDate dataAvaliacao;

    @Column(name = "data_entrevista")
    private LocalDate dataEntrevista;

    @Column(name = "data_resposta")
    private LocalDate dataResposta;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "situacao")
    private Situacao situacao;
}
