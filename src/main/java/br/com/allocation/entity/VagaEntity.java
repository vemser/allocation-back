package br.com.allocation.entity;

import br.com.allocation.enums.Situacao;
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
@Entity(name = "vaga")
public class VagaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VAGA_SEQ")
    @SequenceGenerator(name = "VAGA_SEQ", sequenceName = "seq_vaga", allocationSize = 1)
    @Column(name = "codigo_vaga")
    private Integer codigo;

    @Column(name = "id_cliente", insertable = false, updatable = false)
    private Integer idCliente;

    @Column(name = "id_programa", insertable = false, updatable = false)
    private Integer idPrograma;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteEntity cliente;

    @JsonIgnore
    @OneToMany(mappedBy = "vaga", fetch = FetchType.LAZY)
    private Set<AvaliacaoEntity> avaliacoes = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programa", referencedColumnName = "id_programa")
    private ProgramaEntity programa;

    @JsonIgnore
    @OneToMany(mappedBy = "vaga", fetch = FetchType.LAZY)
    private Set<ReservaAlocacaoEntity> resevasAlocacoes;
}
