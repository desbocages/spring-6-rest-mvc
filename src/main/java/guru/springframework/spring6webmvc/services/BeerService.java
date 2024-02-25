package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<List<Beer>> listBeers();

    Optional<Beer> getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeer(UUID id, Beer beer);

    void deleteById(UUID id);

     void patchBeerById(UUID id, Beer beer1);
}
