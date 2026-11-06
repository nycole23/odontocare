
package senai499.com.br.odontocare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import senai499.com.br.odontocare.repository.UsuarioRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(
            UsuarioRepository usuarioRepository) {

        return email -> usuarioRepository.findByEmail(email)
                .map(usuario -> User.withUsername(usuario.getEmail())
                        .password(usuario.getSenha())
                        .roles(usuario.getPerfil().name())
                        .build())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Páginas públicas
                        .requestMatchers(
                                "/", "/sobre", "/servicos",
                                "/login", "/cadastro",
                                "/css/**", "/js/**", "/images/**"
                        ).permitAll()

                        // Área exclusiva do cliente
                        .requestMatchers("/cliente/**").hasRole("CLIENTE")

                        // Área exclusiva do funcionário
                        .requestMatchers("/funcionario/**").hasRole("FUNCIONARIO")

                        // Demais páginas exigem autenticação
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")

                        // Redireciona de acordo com o perfil
                        .successHandler((request, response, authentication) -> {

                            boolean cliente = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));

                            boolean funcionario = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_FUNCIONARIO"));

                            if (cliente) {
                                response.sendRedirect("/cliente");
                            } else if (funcionario) {
                                response.sendRedirect("/funcionario");
                            } else {
                                response.sendRedirect("/");
                            }
                        })

                        .failureUrl("/login?erro")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
