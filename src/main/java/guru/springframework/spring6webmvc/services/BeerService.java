package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    Beer updateBeer(UUID id,Beer beer);

    void deleteById(UUID id);

    public void patchBeerById(UUID id, Beer beer1);
}
