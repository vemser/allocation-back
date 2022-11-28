package br.com.allocation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "tecnologia")
public class TecnologiaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TECNOLOGIA")
    @SequenceGenerator(name = "SEQ_TECNOLOGIA", sequenceName = "seq_tecnologia", allocationSize = 1)
    @Column(name = "id_tecnologia")
    private Integer idTecnologia;

    @Column(name = "nome")
    private String nome;


}
