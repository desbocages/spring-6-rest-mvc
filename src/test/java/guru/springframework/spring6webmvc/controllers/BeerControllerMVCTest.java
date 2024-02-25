package guru.springframework.spring6webmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6webmvc.domain.Beer;
import guru.springframework.spring6webmvc.services.BeerService;
import guru.springframework.spring6webmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static guru.springframework.spring6webmvc.controllers.BeerController.BEER_BASE_PATH;
import static guru.springframework.spring6webmvc.controllers.BeerController.BEER_VAR_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
public class BeerControllerMVCTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BeerService beerService;
    @Autowired
    ObjectMapper objectMapper;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> argumentCaptor;

    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testThrowExceptionWithMockito() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BEER_VAR_PATH_ID,UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetBeerById() throws Exception {
        Beer mockBeer = getList().get(0);
        given(beerService.getBeerById(mockBeer.getId())).willReturn(Optional.of(mockBeer));
        mockMvc.perform(get(
                        BEER_VAR_PATH_ID, mockBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",is(mockBeer.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.beerName",is(mockBeer.getBeerName())));
    }

    @Test
    void testListBeer() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());
        mockMvc.perform(get(BEER_BASE_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        is(getList().size())));
    }

    List<Beer> getList(){
        return ((Optional<List<Beer>>)beerServiceImpl.listBeers()).get();
    }
    @Test
    void testCreateNewBeer() throws Exception {
        Beer aux = getList().get(0);
        aux.setId(null);
        aux.setVersion(null);
        given(beerService.saveNewBeer(any(Beer.class))).willReturn(getList().get(1));
        mockMvc.perform(post(BEER_BASE_PATH).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aux)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateBeer() throws Exception {
        Beer aux = getList().get(0);
        //given(beerService.updateBeer(aux.getId(),aux)).willReturn(null);
        mockMvc.perform(put(BEER_VAR_PATH_ID,aux.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aux)))
                .andExpect(status().isNoContent());
        verify(beerService).updateBeer(any(UUID.class),any(Beer.class));
    }

    @Test
    void testDeleteBeerById() throws Exception {
        Beer beer = getList().get(0);
        mockMvc.perform(delete(BEER_VAR_PATH_ID,beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(beerService).deleteById(argumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(argumentCaptor.getValue());
    }

    @Test
    void testPatchBeerById() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get().get(0);
        Map<String,String> params = new HashMap<>();
        params.put("beerName","New beer name");
        mockMvc.perform(patch(BEER_VAR_PATH_ID,beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(argumentCaptor.capture(),beerArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(argumentCaptor.getValue());
        assertThat(params.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }
}
