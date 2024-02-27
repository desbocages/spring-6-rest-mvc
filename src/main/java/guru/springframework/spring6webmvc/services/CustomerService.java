package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerService {
     Optional<CustomerDTO> getById(UUID id);
     List<CustomerDTO> listCustomers();

     CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO savedCustomer);

    void deleteCustomer(UUID id);

    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
