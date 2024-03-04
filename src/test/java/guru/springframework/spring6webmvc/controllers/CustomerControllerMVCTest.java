package guru.springframework.spring6webmvc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6webmvc.domain.CustomerDTO;
import guru.springframework.spring6webmvc.services.CustomerService;
import guru.springframework.spring6webmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

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
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testCreateCustomerNullName() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .version(-1).build();
        given(customerService.saveNewCustomer(any(CustomerDTO.class)))
                .willReturn(customerServiceImpl.listCustomers().get(0));
        MvcResult result = mockMvc.perform(
                post(CUSTOMER_BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO))
       ).andExpect(status().isBadRequest()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerDTO mockCustomer = getList().get(0);
        given(customerService.getById(mockCustomer.getId())).willReturn(Optional.of(mockCustomer));
        mockMvc.perform(get(CUSTOMER_VAR_PATH_ID, mockCustomer.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName", is(mockCustomer.getCustomerName())));
    }

    @Test
    void testThrowCustomExceptionWithMockito() throws Exception {
        given(customerService.getById(any(UUID.class))).willThrow(new NotfoundException());
        mockMvc.perform(get(CUSTOMER_VAR_PATH_ID,UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListCustomer() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());
        mockMvc.perform(get(CUSTOMER_BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(getList().size())));

    }

    List<CustomerDTO> getList(){
        return customerServiceImpl.listCustomers();
    }

    @Test
    void testCreateNewCustomer() throws Exception {
        CustomerDTO aux = getList().get(0);
        aux.setVersion(null);
        aux.setId(null);

        given(customerService.saveNewCustomer(any(CustomerDTO.class))).willReturn(getList().get(1));

        mockMvc.perform(post(CUSTOMER_BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aux)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateCustomerById() throws Exception {
        CustomerDTO customer = getList().get(0);
        given(customerService.updateCustomer(any(UUID.class),any(CustomerDTO.class)))
                .willReturn(Optional.of(customer));
        mockMvc.perform(put(CUSTOMER_VAR_PATH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());
        verify(customerService).updateCustomer(any(UUID.class), any(CustomerDTO.class));
    }

    @Test
    void testDeleteCustomerById() throws Exception {
        CustomerDTO customer = getList().get(0);
        mockMvc.perform(delete(CUSTOMER_VAR_PATH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(customerService).deleteCustomer(argumentCaptor.capture());

        assertThat(customer.getId().equals(argumentCaptor.getValue()));
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO aux = getList().get(0);
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
