package com.ampmap.ampmap.dtos;

//import com.ampmap.ampmap.model.entities.Cars;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioDTO(
        @NotBlank(message = "Campo obrigatorio!")
        String nome,
        @NotBlank(message = "Campo obrigatorio!")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, incluindo pelo menos uma letra maiúscula e um número.")
        String senha,
        @NotBlank(message = "Campo obrigatorio!")
        @Email(message = "Email invalido!")
        String email,
        @NotBlank(message = "Campo obrigatorio!")
        Boolean station_owner
//        List<Cars> cars
) {
}
