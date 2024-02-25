package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.Beer;
import guru.springframework.spring6webmvc.domain.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public Beer saveNewBeer(Beer beer) {
        Objects.requireNonNull(beer,"The beer to save should not be null.");
        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .version(1)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .price(beer.getPrice())
                .upc("1234")
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public Beer updateBeer(UUID id,Beer beer){
        Beer former = getBeerById(id);
        Beer savedBeer = Beer.builder()
                .id(beer.getId())
                .creationDate(former.getCreationDate())
                .updateDate(LocalDateTime.now())
                .version(former.getVersion()+1)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .price(beer.getPrice())
                .upc(beer.getUpc())
                .build();
        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public void deleteById(UUID id) {
        beerMap.remove(id);
    }

    @Override
    public void patchBeerById(UUID id, Beer beer1) {
        Beer existingBeer = beerMap.get(id);
        if(existingBeer!=null){
            if(StringUtils.hasText(beer1.getBeerName())){
                existingBeer.setBeerName(beer1.getBeerName());
            }
            if(beer1.getBeerStyle()!=null){
                existingBeer.setBeerStyle(beer1.getBeerStyle());
            }
            if(beer1.getPrice()!=null){
                existingBeer.setPrice(beer1.getPrice());
            }
            if(StringUtils.hasText(beer1.getUpc())){
                existingBeer.setUpc(beer1.getUpc());
            }
            existingBeer.setVersion(existingBeer.getVersion()+1);
            //updateBeer(existingBeer);
        }
    }
}
