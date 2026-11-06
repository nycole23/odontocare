package senai499.com.br.odontocare.controller;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Página inicial
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Página sobre a clínica
    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }

    // Página de serviços
    @GetMapping("/servicos")
    public String servicos() {
        return "servicos";
    }

    // Página de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
