
package senai499.com.br.odontocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import senai499.com.br.odontocare.dto.CadastroFuncionarioDTO;
import senai499.com.br.odontocare.service.FuncionarioService;

@Controller
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/funcionario")
    public String index() {
        return "funcionario/index";
    }

    @GetMapping("/funcionario/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("funcionario", new CadastroFuncionarioDTO());
        return "funcionario/cadastro-funcionario";
    }

    @PostMapping("/funcionario/cadastro")
    public String cadastrar(
            @ModelAttribute("funcionario") CadastroFuncionarioDTO dados,
            Model model) {

        try {
            funcionarioService.cadastrar(dados);
            return "redirect:/funcionario/funcionarios?cadastroSucesso";

        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "funcionario/cadastro-funcionario";
        }
    }

    @GetMapping("/funcionario/funcionarios")
    public String listar(Model model) {
        model.addAttribute("funcionarios", funcionarioService.listarTodos());
        return "funcionario/funcionarios";
    }
}
