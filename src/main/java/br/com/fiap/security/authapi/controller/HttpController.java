package br.com.fiap.security.authapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {

    @GetMapping("/admin")
    public String adminRoute() {
        return "<h1> ADMIN, você está acessando o área administrativa do app! </h1>";
    }

    @GetMapping("/user")
    public String userRoute() {
        return "<h1> USER, você está acessando a área geral do app! </h1>";
    }

    @GetMapping("/public")
    public String publicRoute() {
        return "<h1> Público, você está acessando uma área pública do app! </h1>";
    }

    @GetMapping("/login")
    public String loginRoute() {
        return "login";
    }
}