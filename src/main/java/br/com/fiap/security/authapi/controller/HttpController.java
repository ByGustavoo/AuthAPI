package br.com.fiap.security.authapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {

    @GetMapping("/public")
    public String publicRoute() {
        return "<h1> Rota Pública, sinta-se livre para acessar! </h1>";
    }

    @GetMapping("/private")
    public String privateRoute() {
        return "<h1> Rota Privada, somente pessoas autorizadas! </h1>";
    }
}