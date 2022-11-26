package br.com.allocation.entity;

import br.com.allocation.enums.Situacao;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;


@Entity(name = "Cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENTE")
    @SequenceGenerator(name = "SEQ_CLIENTE", sequenceName = "seq_cliente", allocationSize = 1)
    @Column(name = "ID_CLIENTE")
    private Integer idCliente;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "situacao")
    private Situacao situacao;
}
