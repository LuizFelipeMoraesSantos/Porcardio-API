package br.com.procardio.api.dto;

import br.com.procardio.api.enums.Perfil;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UsuarioDTO(
        @NotBlank
    String nome,
        @NotBlank
    String email,
        @NotBlank
    String senha,
        String cep,
        String numero,
        String complemento,
        Set<Perfil> perfis
) {

    public Object perfil() {
        return null;
    }
}
