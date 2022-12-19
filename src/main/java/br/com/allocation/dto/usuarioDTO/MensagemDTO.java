package br.com.allocation.dto.usuariodto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MensagemDTO {
    private String message;

    public String getMessage() {
        return message;
    }
}
