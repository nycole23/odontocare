
package senai499.com.br.odontocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import senai499.com.br.odontocare.dto.CadastroClienteDTO;
import senai499.com.br.odontocare.service.ClienteService;

@Controller
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Abre a página de cadastro
    @GetMapping("/cadastro")
    public String cadastro(Model model) {

        model.addAttribute("cliente", new CadastroClienteDTO());

        return "cadastro";
    }

    // Recebe os dados do formulário
    @PostMapping("/cadastro")
    public String cadastrar(
            @ModelAttribute("cliente") CadastroClienteDTO dados,
            Model model) {

        try {

            clienteService.cadastrar(dados);

            return "redirect:/login?cadastroSucesso";

        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());

            return "cadastro";
        }
    }
}
