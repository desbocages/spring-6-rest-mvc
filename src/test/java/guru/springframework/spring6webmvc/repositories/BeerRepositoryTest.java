package guru.springframework.spring6webmvc.repositories;

import guru.springframework.spring6webmvc.domain.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testCreateNewBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("KING BEER")
                .build());
        assertThat(savedBeer.getBeerName()).isNotBlank();
        assertThat(savedBeer.getId()).isNotNull();
    }
}