package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.domain.Beer;
import guru.springframework.spring6webmvc.services.BeerService;
import guru.springframework.spring6webmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

@WebMvcTest(BeerController.class)
public class BeerControllerMVCTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();
    @Test
    void testGetBeerById() throws Exception {
        Beer mockBeer = beerServiceImpl.listBeers().get(0);
        given(beerService.getBeerById(ArgumentMatchers.any(UUID.class))).willReturn(mockBeer);
        mockMvc.perform(get(
                "/api/v1/beer/"+ UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
