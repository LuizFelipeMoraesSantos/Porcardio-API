package br.com.procardio.api.service;

import br.com.procardio.api.model.Usuario;
import br.com.procardio.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class GoogleAuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario processarUsuarioGoogle(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");


        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (Objects.isNull(usuario)) {

            usuario = new Usuario();

            usuario.setNome(name);
            usuario.setEmail(email);
            usuario.setSenha(passwordEncoder.encode(UUID.randomUUID().toString()));

            return usuarioRepository.save(usuario);
        }


        return usuario;
    }
}