package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(CustomerController.class)
public class CustomerControllerMVCTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;

    @Test
    void testGetCustomerById() throws Exception {
        mockMvc.perform(get("/api/v1/customer/"+ UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON));
    }
}
