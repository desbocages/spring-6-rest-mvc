package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer);

    boolean deleteById(UUID id);

     void patchBeerById(UUID id, BeerDTO beer1);
}
