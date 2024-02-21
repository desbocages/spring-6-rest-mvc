package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.Beer;
import guru.springframework.spring6webmvc.domain.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {
    Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        beerMap = new HashMap<>();
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("King")
                .beerStyle(BeerStyle.MALTED)
                .upc("nothing")
                .price(new BigDecimal(650))
                .quantityOnHand(55)
                .version(1)
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("33 Export")
                .beerStyle(BeerStyle.MALTED)
                .upc("nothing")
                .price(new BigDecimal(650))
                .quantityOnHand(75)
                .version(1)
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Booster Cola")
                .beerStyle(BeerStyle.WHISKIED)
                .upc("nothing")
                .price(new BigDecimal(650))
                .quantityOnHand(10)
                .version(1)
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }


    @Override
    public Beer getBeerById(UUID id) {

        log.debug("The method getBeerById has been called with id: " + id);
        return beerMap.get(id);
    }
}
