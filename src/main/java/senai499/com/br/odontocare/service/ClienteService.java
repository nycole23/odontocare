
package senai499.com.br.odontocare.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import senai499.com.br.odontocare.dto.CadastroClienteDTO;
import senai499.com.br.odontocare.model.Cliente;
import senai499.com.br.odontocare.model.Usuario;
import senai499.com.br.odontocare.model.PerfilUsuario;
import senai499.com.br.odontocare.repository.ClienteRepository;
import senai499.com.br.odontocare.repository.UsuarioRepository;

@Service
public class ClienteService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void cadastrar(CadastroClienteDTO dados) {

        // Verifica se as senhas são iguais
        if (!dados.getSenha().equals(dados.getConfirmarSenha())) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }

        // Verifica se o e-mail já existe
        if (usuarioRepository.existsByEmail(dados.getEmail())) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado.");
        }

        // Cria o usuário
        Usuario usuario = new Usuario();

        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());

        // Criptografa a senha
        usuario.setSenha(passwordEncoder.encode(dados.getSenha()));

        // Define o perfil automaticamente
        usuario.setPerfil(PerfilUsuario.CLIENTE);

        usuarioRepository.save(usuario);

        // Cria o cliente
        Cliente cliente = new Cliente();

        cliente.setCpf(dados.getCpf());
        cliente.setTelefone(dados.getTelefone());
        cliente.setUsuario(usuario);

        clienteRepository.save(cliente);
    }
}
