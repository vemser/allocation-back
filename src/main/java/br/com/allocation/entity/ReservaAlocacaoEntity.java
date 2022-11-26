package br.com.allocation.entity;

import br.com.allocation.enums.Situacao;
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
    private Integer codigo;

    @Column(name = "id_aluno")
    private Integer idAluno;

    @Column(name = "codigo_vaga", insertable = false, updatable = false)
    private Integer codigoVaga;

    @Column(name = "codigo_avaliacao", insertable = false, updatable = false)
    private Integer codigoAvaliacao;

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
    private Situacao situacao;

    @JsonIgnore
    @OneToOne
    @MapsId
    private AlunoEntity aluno;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_vaga", referencedColumnName = "codigo_vaga")
    private VagaEntity vaga;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "codigo_avaliacao", referencedColumnName = "codigo_avaliacao")
    private AvaliacaoEntity avaliacao;
}
