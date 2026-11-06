
package senai499.com.br.odontocare.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import senai499.com.br.odontocare.dto.CadastroServicoDTO;
import senai499.com.br.odontocare.model.Servico;
import senai499.com.br.odontocare.repository.ServicoRepository;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @Transactional
    public void cadastrar(CadastroServicoDTO dados) {

        if (dados.getNome() == null || dados.getNome().isBlank()) {
            throw new IllegalArgumentException("Informe o nome do serviço.");
        }

        if (dados.getDescricao() == null || dados.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Informe a descrição do serviço.");
        }

        if (dados.getPreco() == null || dados.getPreco().signum() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }

        if (dados.getDuracaoMinutos() == null ||
                dados.getDuracaoMinutos() <= 0) {
            throw new IllegalArgumentException("Informe uma duração válida.");
        }

        if (servicoRepository.existsByNomeIgnoreCase(dados.getNome().trim())) {
            throw new IllegalArgumentException("Este serviço já está cadastrado.");
        }

        Servico servico = new Servico();

        servico.setNome(dados.getNome().trim());
        servico.setDescricao(dados.getDescricao().trim());
        servico.setPreco(dados.getPreco());
        servico.setDuracaoMinutos(dados.getDuracaoMinutos());
        servico.setAtivo(true);

        servicoRepository.save(servico);
    }

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    public List<Servico> listarAtivos() {
        return servicoRepository.findByAtivoTrue();
    }

    @Transactional
    public void alterarStatus(Long id) {

        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Serviço não encontrado."));

        servico.setAtivo(!servico.getAtivo());

        servicoRepository.save(servico);
    }
}