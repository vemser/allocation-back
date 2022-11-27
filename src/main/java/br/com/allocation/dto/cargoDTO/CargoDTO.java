package br.com.allocation.dto.cargoDTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CargoDTO {
    @NotNull
    private String nome;
}
