package br.com.allocation.entity;

import br.com.allocation.enums.SituacaoAllocation;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "Reserva_Alocacao")
public class ReservaAlocacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVA_ALOCACAO_SEQ")
    @SequenceGenerator(name = "RESERVA_ALOCACAO_SEQ", sequenceName = "seq_reserva_alocacao", allocationSize = 1)
    @Column(name = "codigo_reserva_alocacao")
    private Integer idReservaAlocacao;

    @Column(name = "id_aluno", insertable = false, updatable = false)
    private Integer idAluno;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_reserva")
    private LocalDate dataReserva;

    @Column(name = "data_alocacao")
    private LocalDate dataAlocacao;

    @Column(name = "data_cancelamento")
    private LocalDate dataCancelamento;

    @Column(name = "data_finalizado")
    private LocalDate dataFinalizado;

    @Column(name = "situacao")
    @Enumerated(EnumType.STRING)
    private SituacaoAllocation situacaoAllocation;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_aluno", referencedColumnName = "id_aluno")
    private AlunoEntity aluno;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "codigo_vaga", referencedColumnName = "codigo_vaga")
    private VagaEntity vaga;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "codigo_avaliacao", referencedColumnName = "codigo_avaliacao")
    private AvaliacaoEntity avaliacao;
}
