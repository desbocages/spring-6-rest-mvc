package guru.springframework.spring6webmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6webmvc.domain.BeerDTO;
import guru.springframework.spring6webmvc.domain.BeerStyle;
import guru.springframework.spring6webmvc.domain.entities.Beer;
import guru.springframework.spring6webmvc.mappers.BeerMapper;
import guru.springframework.spring6webmvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import static org.hamcrest.core.Is.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static guru.springframework.spring6webmvc.controllers.BeerController.BEER_VAR_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {
    @Autowired
BeerController beerController;

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerMapper beerMapper;

    @Autowired
    WebApplicationContext wac;
    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

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
    void testPatchBeerByIdNameTooLong() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        Map<String,String> params = new HashMap<>();
        params.put("beerName","New beer name27276363563636366736737373637373737938387837387373");
       MvcResult result = mockMvc.perform(patch(BEER_VAR_PATH_ID,beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(1))).andReturn();

        System.out.println(result.getResponse().getContentAsString());
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