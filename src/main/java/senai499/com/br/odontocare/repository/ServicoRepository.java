
package senai499.com.br.odontocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senai499.com.br.odontocare.model.Servico;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByAtivoTrue();

    boolean existsByNomeIgnoreCase(String nome);
}