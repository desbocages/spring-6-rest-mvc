package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.domain.BeerDTO;
import guru.springframework.spring6webmvc.domain.BeerStyle;
import guru.springframework.spring6webmvc.domain.CustomerDTO;
import guru.springframework.spring6webmvc.domain.entities.Beer;
import guru.springframework.spring6webmvc.domain.entities.Customer;
import guru.springframework.spring6webmvc.mappers.CustomerMapper;
import guru.springframework.spring6webmvc.repositories.CustomerRepository;
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
@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CustomerRepository customerRepository;
    @Test
    void getCustomerById() {
        List<CustomerDTO> customerList = customerController.listCustomers();
        CustomerDTO customerDTO = customerList.get(0);
        assertThat(customerRepository.findById(customerDTO.getId()).get().getId())
                .isEqualTo(customerController.getById(customerDTO.getId()).getId());
    }

    @Test
    void getCustomerByIdNotFound() {
        assertThrows(NotfoundException.class,
                ()->{
                   customerController.getById(UUID.randomUUID());
                });
    }

    @Test
    void listCustomers() {
        List<CustomerDTO> customerList = customerController.listCustomers();
        assertThat(customerList.size()).isEqualTo(3);
    }

    @Test
    @Rollback
    @Transactional
    void listCustomersEmpty() {
        customerRepository.deleteAll();
        List<CustomerDTO> customerList = customerController.listCustomers();
        assertThat(customerList.size()).isEqualTo(0);
    }

    @Test
    @Rollback
    @Transactional
    void testCreateNewCustomer() {
        Customer customer = new Customer();
        String name = "New Name";
        customer.setCustomerName(name);
        CustomerDTO beerDTO = customerMapper.customerToCustomerDTO(customer);
        ResponseEntity result = customerController.saveNewCustomer(beerDTO);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getHeaders().getLocation()).isNotNull();
        String id = result.getHeaders().getLocation().getPath().split("/")[4];
        assertThat(id).isNotNull();
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void patchCustomerById() {
    }
}