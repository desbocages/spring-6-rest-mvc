package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.CustomerDTO;
import guru.springframework.spring6webmvc.mappers.CustomerMapper;
import guru.springframework.spring6webmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class CustomerServiceJPAImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;
    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        return Optional.ofNullable(
                customerMapper.customerToCustomerDTO(
                        customerRepository.findById(id).orElse(null)));
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        return customerMapper.customerToCustomerDTO(
                customerRepository.save(
                        customerMapper.customerDTOToCustomer(customer)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO savedCustomer) {
        AtomicReference<Optional<CustomerDTO>> atomicReferenceOptional = new AtomicReference<>();
        customerRepository.findById(id).ifPresentOrElse(foundCustomer ->{
            foundCustomer.setCustomerName(savedCustomer.getCustomerName());
            atomicReferenceOptional.set(Optional.of(customerMapper.customerToCustomerDTO(
                    customerRepository.save(foundCustomer))));
        },()->{
            atomicReferenceOptional.set(Optional.empty());
        });
        return atomicReferenceOptional.get();
    }

    @Override
    public void deleteCustomer(UUID id) {

    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
}
