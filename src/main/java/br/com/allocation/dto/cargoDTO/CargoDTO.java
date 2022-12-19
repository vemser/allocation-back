package br.com.allocation.dto.cargodto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CargoDTO {
    @NotNull
    private String nome;
}
