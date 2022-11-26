package br.com.allocation.dto.usuarioDTO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;

@Data
public class FileDTO {

    private String name;

    private String fileURI;

    private String type;

    private Integer data;
}
