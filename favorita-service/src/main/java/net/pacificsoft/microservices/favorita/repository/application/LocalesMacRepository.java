package net.pacificsoft.microservices.favorita.repository.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import net.pacificsoft.microservices.favorita.models.application.Locales;
import net.pacificsoft.microservices.favorita.models.application.LocalesMac;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface LocalesMacRepository extends JpaRepository<LocalesMac, Long>{

}
