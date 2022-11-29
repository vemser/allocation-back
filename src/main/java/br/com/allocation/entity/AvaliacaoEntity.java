package br.com.allocation.entity;

import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.SituacaoAluno;
import br.com.allocation.enums.TipoAvaliacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Avaliacao")
public class AvaliacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AVALIACAO")
    @SequenceGenerator(name = "SEQ_AVALIACAO", sequenceName = "seq_avaliacao", allocationSize = 1)
    @Column(name = "codigo_avaliacao")
    private Integer codigo;

    @Column(name = "id_aluno", insertable = false, updatable = false)
    private Integer idAluno;

    @Column(name = "codigo_vaga", insertable = false, updatable = false)
    private Integer codigoVaga;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "nota")
    private Double nota;

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
    private SituacaoAluno situacao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", referencedColumnName = "id_aluno")
    private AlunoEntity aluno;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_vaga", referencedColumnName = "codigo_vaga")
    private VagaEntity vaga;

    @JsonIgnore
    @OneToMany(mappedBy = "avaliacao", fetch = FetchType.LAZY)
    private Set<ReservaAlocacaoEntity> reservasAlocacoes = new HashSet<>();

}
