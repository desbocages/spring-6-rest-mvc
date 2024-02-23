package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.services.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.UUID;

@WebMvcTest(BeerController.class)
public class BeerControllerMVCTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BeerService beerService;
    @Test
    void testGetBeerById() throws Exception {
        mockMvc.perform(get(
                "/api/v1/beer/"+ UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON));
    }
}
