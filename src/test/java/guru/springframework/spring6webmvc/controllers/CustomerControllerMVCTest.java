package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.domain.Customer;
import guru.springframework.spring6webmvc.services.BeerService;
import guru.springframework.spring6webmvc.services.CustomerService;
import guru.springframework.spring6webmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerMVCTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();


    @Test
    void testGetCustomerById() throws Exception {
        Customer mockCustomer = customerServiceImpl.listCustomers().get(0);
        BDDMockito.given(customerService.getById(any(UUID.class))).willReturn(mockCustomer);
        mockMvc.perform(get("/api/v1/customer/"+ UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
