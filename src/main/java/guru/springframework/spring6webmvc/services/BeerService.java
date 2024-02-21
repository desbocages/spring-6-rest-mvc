package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();

    Beer getBeerById(UUID id);
}