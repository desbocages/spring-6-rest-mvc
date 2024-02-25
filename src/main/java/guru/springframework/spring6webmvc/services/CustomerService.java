package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.Beer;
import guru.springframework.spring6webmvc.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerService {
     Optional<Customer> getById(UUID id);
     Optional<List<Customer>> listCustomers();

     Customer saveNewCustomer(Customer customer);

    Customer updateCustomer(UUID id,Customer savedCustomer);

    void deleteCustomer(UUID id);

    void patchCustomerById(UUID customerId, Customer customer);
}
