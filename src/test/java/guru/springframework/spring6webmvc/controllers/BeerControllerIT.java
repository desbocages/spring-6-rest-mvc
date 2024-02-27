package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.domain.BeerDTO;
import guru.springframework.spring6webmvc.domain.BeerStyle;
import guru.springframework.spring6webmvc.domain.entities.Beer;
import guru.springframework.spring6webmvc.mappers.BeerMapper;
import guru.springframework.spring6webmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BeerControllerIT {
    @Autowired
BeerController beerController;

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerMapper beerMapper;
    @Test
    void testPopulatedListBeers() {
        List<BeerDTO> beerList = beerController.listBeers();
        assertThat(beerList.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyListBeers() {
        beerRepository.deleteAll();
        List<BeerDTO> beerList = beerController.listBeers();
        assertThat(beerList.size()).isEqualTo(0);
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateBeerById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerMapper.beerToBeerDTO(beer);
        String name = "UPDATED";
        dto.setBeerName(name);
       ResponseEntity result = beerController.updateBeer(beer.getId(),dto);
       assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
       assertThat(beerRepository.findById(beer.getId()).get().getBeerName()).isEqualTo(name);

    }

    @Test
    void testUpdateBeerByIdNotFound() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO dto = beerMapper.beerToBeerDTO(beer);
        assertThrows(NotfoundException.class,()->{
            beerController.updateBeer(UUID.randomUUID(),dto);
        });
    }

    @Test
    void testDeleteBeerById() {
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity result = beerController.deleteBeerById(beer.getId());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId()).isEmpty());
    }

    @Test
    void getBeerById() {
        List<BeerDTO> beerList = beerController.listBeers();
        BeerDTO beer = beerList.get(0);
        assertThat(beerRepository.findById(beer.getId()).get().getId())
                .isEqualTo(beerController.getBeerById(beer.getId()).getId());
    }

    @Test
    void getBeerByIdGeneratingNotFoundException() {

        assertThrows(NotfoundException.class,()->{
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    @Rollback
    @Transactional
    void testCreateNewBeer() {
        Beer beer = new Beer();
        String name = "New Name";
        beer.setBeerStyle(BeerStyle.MALTED);
        beer.setUpc("upc");
        beer.setId(null);
        beer.setVersion(null);
        beer.setBeerName(name);
        beer.setQuantityOnHand(5);
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
        ResponseEntity result = beerController.saveNewBeer(beerDTO);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        String id = result.getHeaders().getLocation().getPath().split("/")[4];
        assertThat(id).isNotNull();

    }
}