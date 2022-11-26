package br.com.allocation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_SEQ")
    @SequenceGenerator(name = "FILE_SEQ", sequenceName = "seq_file", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "tipo")
    private String type;

    @Column(name = "dado")
    @Lob
    private byte[] data;

    @JsonIgnore
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_USUARIO",referencedColumnName = "ID_USUARIO")
    private UsuarioEntity usuario;

}
