package net.pacificsoft.springbootcrudrest.repository;

import net.pacificsoft.springbootcrudrest.model.Locales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalesRepository extends JpaRepository<Locales, Long>{

}
