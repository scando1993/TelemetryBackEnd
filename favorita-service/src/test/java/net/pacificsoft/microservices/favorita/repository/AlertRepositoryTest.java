package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Alert;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class AlertRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AlertRepository alertRepository;

    @Test
    public void whenFindByTypeAlert_thenReturnAlert(){
        //Data given
        Alert alert = new Alert();
        entityManager.persist(alert);
        entityManager.flush();

        //When
        Alert found = alertRepository.findByType_alert(alert.getType_alert());

        //then
        assertThat(found.getType_alert())
                .isEqualTo(alert.getType_alert());

    }

    @After
    public void cleanUp(){
        alertRepository.deleteAll();
    }
}