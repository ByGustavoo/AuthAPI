package br.com.fiap.security.authapi.config;

import br.com.fiap.security.authapi.service.CustomUserDeitailsService;
import br.com.fiap.security.authapi.service.PlainTextPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDeitailsService customUserDeitailsService;

    /*
     * Configuração padrão com o BCrypt
    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    */

    /**
     * Configuração Personalizada
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PlainTextPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDeitailsService);

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/public/**").permitAll();
                    authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN");
                    authorizeRequests.requestMatchers("/user/**").hasRole("USER");
                    authorizeRequests.anyRequest().authenticated();
                }).formLogin(form -> form.successHandler(customAuthenticationSuccessHandler()))
                .logout(LogoutConfigurer::permitAll);

        return httpSecurity.build();
    }

    // Configuração de um AuthenticationSuccessHandler para redirecionar usuários com base na role
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        // Método que será executado quando a autenticação for bem-sucedida
        return (request, response, authentication) -> {
            // Verifica se o usuário autenticado possui a role "ROLE_ADMIN"
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                // Redireciona o usuário com a role "ROLE_ADMIN" para a página "/admin"
                response.sendRedirect("/AuthAPI/admin");
                // Verifica se o usuário possui a role "ROLE_USER"
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                // Redireciona o usuário com a role "ROLE_USER" para a página "/user"
                response.sendRedirect("/AuthAPI/user");
                // Caso o usuário não tenha uma role específica definida
            } else {
                // Redireciona o usuário para a raiz "/" como fallback padrão
                response.sendRedirect("/");
            }
        };
    }
}