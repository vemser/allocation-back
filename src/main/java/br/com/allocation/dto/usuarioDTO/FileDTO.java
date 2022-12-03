package br.com.allocation.dto.usuarioDTO;

import lombok.Data;

@Data
public class FileDTO {

    private Integer id;

    private String name;

    private String type;

    private byte[] data;
}
