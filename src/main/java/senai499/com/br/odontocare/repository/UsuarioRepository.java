package senai499.com.br.odontocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senai499.com.br.odontocare.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}