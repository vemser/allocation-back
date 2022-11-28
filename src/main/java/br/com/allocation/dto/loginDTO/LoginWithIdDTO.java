package br.com.allocation.dto.loginDTO;

import br.com.allocation.entity.CargoEntity;
import br.com.allocation.enums.Cargos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class LoginWithIdDTO {
    @NotNull
    private Integer idUsuario;

    @NotBlank
    private String nomeCompleto;

    @JsonIgnore
    @NotNull
    @Schema(example = "123")
    private String senha;

    private Set<CargoEntity> cargos;
    @NotNull
    @NotBlank
    @Email
    private String email;
}
