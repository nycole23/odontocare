
package senai499.com.br.odontocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senai499.com.br.odontocare.model.Funcionario;

import java.util.Optional;

public interface FuncionarioRepository
        extends JpaRepository<Funcionario, Long> {

    boolean existsByCro(String cro);

    Optional<Funcionario> findByUsuarioId(Long usuarioId);
}
