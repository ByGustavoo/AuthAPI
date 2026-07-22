package br.com.fiap.security.authapi.service;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PlainTextPasswordEncoder implements PasswordEncoder {

    @Override
    public @Nullable String encode(@Nullable CharSequence rawPassword) {
        assert rawPassword != null;
        return rawPassword.toString();
    }

    @Override
    public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
        assert rawPassword != null;
        return rawPassword.toString().equals(encodedPassword);
    }
}