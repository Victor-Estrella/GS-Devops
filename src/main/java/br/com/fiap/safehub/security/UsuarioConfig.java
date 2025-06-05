package br.com.fiap.safehub.security;

import br.com.fiap.safehub.entity.CadastroUsuario;
import br.com.fiap.safehub.repository.CadastroUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioConfig {

    @Autowired
    private CadastroUsuarioRepository usuarioRepository;

    @Bean
    UserDetailsService gerarUsuario() {
        return username -> {
            CadastroUsuario usuario = usuarioRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base dados"));

            return User.builder()
                    .username(usuario.getNome())
                    .password(usuario.getSenha())
                    .roles("USER")
                    .build();
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
