package br.com.allocation.dto.usuarioDTO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;

@Data
public class FileDTO {

    private Integer id;

    private String name;

    private String type;

    private byte[] data;
}
