
package senai499.com.br.odontocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senai499.com.br.odontocare.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
