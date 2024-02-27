package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.controllers.CustomerController;
import guru.springframework.spring6webmvc.controllers.NotfoundException;
import guru.springframework.spring6webmvc.domain.CustomerDTO;
import guru.springframework.spring6webmvc.domain.entities.Customer;
import guru.springframework.spring6webmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Transactional
    @Rollback
    void testListCustomersNoData() {
        customerRepository.deleteAll();
        assertThat(customerController.listCustomers().size()).isEqualTo(0);
    }
    @Test
    void testGetCustomerByIdGeneratingNotfoundException() {
        assertThrows(NotfoundException.class,()->{
            customerController.getById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateACustomer() {
        CustomerDTO customerDTO = customerController.listCustomers().get(0);
        customerDTO.setCustomerName("UPDATED");
        ResponseEntity result = customerController.updateCustomer(customerDTO.getId(),customerDTO);
        assertEquals(result.getStatusCode(), HttpStatus.NO_CONTENT);
        Customer beer = customerRepository.findById(customerDTO.getId()).get();
        assertThat(beer.getCustomerName()).isEqualTo(customerDTO.getCustomerName());
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("YAKOL")
                .build();
        ResponseEntity result = customerController.saveNewCustomer(customerDTO);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
        assertThat(result.getHeaders().getLocation()).isNotNull();
        String locationUUID = result.getHeaders().getLocation().getPath().split("/")[4];
        assertThat(customerRepository.findById(
                UUID.fromString(locationUUID))
                .get().getCustomerName()).isEqualTo(customerDTO.getCustomerName());
    }

    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        assertThat(customerController.getById(customer.getId())).isNotNull();
    }

    @Test
    void testListCustomers() {
        assertThat(customerController.listCustomers().size()).isEqualTo(3);
    }


}