package net.pacificsoft.microservices.loka.core.model.DAO;

import net.pacificsoft.microservices.loka.core.model.Analog;
import org.springframework.data.repository.CrudRepository;

public interface AnalogDao extends CrudRepository<Analog, Long> {
}
