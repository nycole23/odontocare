package senai499.com.br.odontocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

import senai499.com.br.odontocare.dto.CadastroServicoDTO;
import senai499.com.br.odontocare.service.ServicoService;

@Controller
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    // Lista todos os serviços
    @GetMapping("/funcionario/servicos")
    public String listar(Model model) {
        model.addAttribute("servicos", servicoService.listarTodos());
        return "funcionario/servicos";
    }

    // Abre o formulário de cadastro
    @GetMapping("/funcionario/servicos/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("servico", new CadastroServicoDTO());
        return "funcionario/cadastro-servico";
    }

    // Recebe os dados do formulário
    @PostMapping("/funcionario/servicos/cadastro")
    public String cadastrar(
            @ModelAttribute("servico") CadastroServicoDTO dados,
            Model model) {

        try {
            servicoService.cadastrar(dados);
            return "redirect:/funcionario/servicos?cadastroSucesso";

        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "funcionario/cadastro-servico";
        }
    }

    // Ativa ou desativa um serviço
    @PostMapping("/funcionario/servicos/{id}/status")
    public String alterarStatus(@PathVariable Long id) {
        servicoService.alterarStatus(id);
        return "redirect:/funcionario/servicos";
    }
}