package guru.springframework.spring6webmvc.repositories;

import guru.springframework.spring6webmvc.domain.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testCreateNewCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder()
                        .customerName("Yale")
                .build());
        assertThat(savedCustomer.getCustomerName()).isNotBlank();
        assertThat(savedCustomer.getId()).isNotNull();
    }
}