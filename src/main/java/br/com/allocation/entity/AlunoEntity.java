package br.com.allocation.entity;

import br.com.allocation.enums.Area;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Aluno")
public class AlunoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALUNO_SEQ")
    @SequenceGenerator(name = "ALUNO_SEQ", sequenceName = "seq_aluno", allocationSize = 1)
    @Column(name = "id_aluno")
    private Integer idAluno;

    @Column (name = "id_programa", insertable = false, updatable = false)
    private Integer idPrograma;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "area")
    private Area area;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "descricao")
    private String descricao;

    @JsonIgnore
    @OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
    private Set<AvaliacaoEntity> avaliacoes = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programa", referencedColumnName = "id_programa")
    private ProgramaEntity programa;

    @JsonIgnore
    @OneToOne(mappedBy = "aluno")
    private ReservaAlocacaoEntity reservaAlocacao;
}
