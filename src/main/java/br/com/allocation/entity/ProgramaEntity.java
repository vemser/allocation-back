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
@Entity(name = "Programa")
public class ProgramaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROGRAMA_SEQ")
    @SequenceGenerator(name = "PROGRAMA_SEQ", sequenceName = "seq_programa", allocationSize = 1)
    @Column(name = "ID_PROGRAMA")
    private Integer idPrograma;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "data_termino")
    private LocalDate dataTermino;

    @Column(name = "situacao")
    private Situacao situacao;
}
