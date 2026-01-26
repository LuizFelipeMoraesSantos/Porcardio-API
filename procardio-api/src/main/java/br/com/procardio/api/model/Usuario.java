package br.com.procardio.api.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.procardio.api.dto.UsuarioDTO;
import br.com.procardio.api.enums.Perfil;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Only use ID for equality
@Entity(name = "tb_usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // Using ID for equals/hashcode matches database identity
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Embedded
    private Endereco endereco;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_perfis", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "perfil")

    private Set<Perfil> perfis = new HashSet<>();

    public void adicionarPerfil(Perfil perfil) {
        this.perfis.add(perfil);
    }


    public static Usuario fromDTO(UsuarioDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());


        if (Objects.nonNull(dto.perfis())) {

            dto.perfis().stream()
                    .filter(Objects::nonNull)
                    .forEach(usuario::adicionarPerfil);
        }


        if (dto.cep() != null || dto.numero() != null || dto.complemento() != null) {
            Endereco endereco = new Endereco();
            endereco.setCep(dto.cep());
            endereco.setNumero(dto.numero());
            endereco.setComplemento(dto.complemento());
            usuario.setEndereco(endereco);
        }

        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis.stream()
                // Ensure your Perfil enum's getRole returns "ROLE_ADMIN", etc.
                .map(perfil -> new SimpleGrantedAuthority(perfil.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Usuario toModel(UsuarioDTO usuarioDTO) {
        return null;
    }
}