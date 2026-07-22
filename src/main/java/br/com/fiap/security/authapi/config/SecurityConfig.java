package br.com.fiap.security.authapi.config;

import br.com.fiap.security.authapi.service.PlainTextPasswordEncoder;
import br.com.fiap.security.authapi.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserSecurityService userSecurityService;

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

    /**
     * Configuração padrão
     * Configurando o Gerenciador de Autenticação
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userSecurityService);

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests.requestMatchers("/AuthAPI/public").permitAll();
            authorizeRequests.requestMatchers("/AuthAPI/logout").permitAll();
            authorizeRequests.anyRequest().authenticated();
        });

        httpSecurity.formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }
}