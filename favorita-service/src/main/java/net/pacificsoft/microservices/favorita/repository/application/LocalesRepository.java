package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Locales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalesRepository extends JpaRepository<Locales, Long>{

}
