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
@Entity(name = "vaga")
public class VagaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VAGA_SEQ")
    @SequenceGenerator(name = "VAGA_SEQ", sequenceName = "seq_vaga", allocationSize = 1)
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nome")
    private String nome;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "quantidade_alocados")
    private Integer quantidadeAlocados;

    @Column(name = "data_abertura")
    private LocalDate dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDate dataFechamento;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "situacao")
    private Situacao situacao;
}
