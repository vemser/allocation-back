package br.com.allocation.entity;

import br.com.allocation.enums.Situacao;
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
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "id_aluno")
    private Integer idAluno;

    @Column(name = "codigo_vaga")
    private Integer codigoVaga;

    @Column(name = "id_avaliacao")
    private Integer idAvaliacao;

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
}
