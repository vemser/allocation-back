package br.com.allocation.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private String nomeCompleto;
    private String email;
    private String foto;
}
