
package senai499.com.br.odontocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class ClienteAreaController {

    @GetMapping("/cliente")
    public String index(Authentication authentication, Model model) {

        model.addAttribute("email", authentication.getName());

        return "cliente/index";
    }
}
