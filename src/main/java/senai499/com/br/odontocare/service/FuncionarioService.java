
package senai499.com.br.odontocare.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import senai499.com.br.odontocare.dto.CadastroFuncionarioDTO;
import senai499.com.br.odontocare.model.Funcionario;
import senai499.com.br.odontocare.model.PerfilUsuario;
import senai499.com.br.odontocare.model.Usuario;
import senai499.com.br.odontocare.repository.FuncionarioRepository;
import senai499.com.br.odontocare.repository.UsuarioRepository;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.funcionarioRepository = funcionarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void cadastrar(CadastroFuncionarioDTO dados) {

        if (!dados.getSenha().equals(dados.getConfirmarSenha())) {
            throw new IllegalArgumentException("As senhas não conferem.");
        }

        if (usuarioRepository.existsByEmail(dados.getEmail())) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado.");
        }

        if (funcionarioRepository.existsByCro(dados.getCro())) {
            throw new IllegalArgumentException("Este CRO já está cadastrado.");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        usuario.setPerfil(PerfilUsuario.FUNCIONARIO);

        usuarioRepository.save(usuario);

        Funcionario funcionario = new Funcionario();

        funcionario.setCro(dados.getCro());
        funcionario.setEspecialidade(dados.getEspecialidade());
        funcionario.setUsuario(usuario);

        funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }
}
