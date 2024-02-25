package guru.springframework.spring6webmvc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6webmvc.domain.Beer;
import guru.springframework.spring6webmvc.domain.Customer;
import guru.springframework.spring6webmvc.services.BeerService;
import guru.springframework.spring6webmvc.services.CustomerService;
import guru.springframework.spring6webmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static guru.springframework.spring6webmvc.controllers.CustomerController.CUSTOMER_BASE_PATH;
import static guru.springframework.spring6webmvc.controllers.CustomerController.CUSTOMER_VAR_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerMVCTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;
    @Autowired
    ObjectMapper objectMapper;
    @Captor
    ArgumentCaptor<UUID> argumentCaptor;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testGetCustomerById() throws Exception {
        Customer mockCustomer = customerServiceImpl.listCustomers().get(0);
        given(customerService.getById(mockCustomer.getId())).willReturn(mockCustomer);
        mockMvc.perform(get(CUSTOMER_VAR_PATH_ID, mockCustomer.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName", is(mockCustomer.getCustomerName())));
    }

    @Test
    void testListCustomer() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());
        mockMvc.perform(get(CUSTOMER_BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(customerServiceImpl.listCustomers().size())));

    }

    @Test
    void testCreateNewCustomer() throws Exception {
        Customer aux = customerServiceImpl.listCustomers().get(0);
        aux.setVersion(null);
        aux.setId(null);

        given(customerService.saveNewCustomer(any(Customer.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        mockMvc.perform(post(CUSTOMER_BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aux)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateCustomerById() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);
        mockMvc.perform(put(CUSTOMER_VAR_PATH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());
        verify(customerService).updateCustomer(any(UUID.class), any(Customer.class));
    }

    @Test
    void testDeleteCustomerById() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);
        mockMvc.perform(delete(CUSTOMER_VAR_PATH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(customerService).deleteCustomer(argumentCaptor.capture());

        assertThat(customer.getId().equals(argumentCaptor.getValue()));
    }

    @Test
    void testPatchCustomer() throws Exception {
        Customer aux = customerServiceImpl.listCustomers().get(0);
        Map<String,String> params = new HashMap<>();
        params.put("customerName","New Customer");
        mockMvc.perform(patch(CUSTOMER_VAR_PATH_ID,aux.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isNoContent());
        verify(customerService).patchCustomerById(argumentCaptor.capture(),customerArgumentCaptor.capture());
        assertThat(aux.getId()).isEqualTo(argumentCaptor.getValue());
        assertThat(params.get("customerName")).isEqualTo(customerArgumentCaptor.getValue().getCustomerName());

    }
}
