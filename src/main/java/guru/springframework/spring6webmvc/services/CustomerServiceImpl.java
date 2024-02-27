package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, CustomerDTO> customerMap;
    public CustomerServiceImpl() {
        log.debug("Initializing the CustomerServiceImpl class...");
        customerMap = new HashMap<>();
        CustomerDTO c1 = CustomerDTO.builder()
                .customerName("Yakam")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        CustomerDTO c2 = CustomerDTO.builder()
                .customerName("Yale")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        CustomerDTO c3 = CustomerDTO.builder()
                .customerName("Yakoubou")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        customerMap.put(c1.getId(),c1);
        customerMap.put(c2.getId(),c2);
        customerMap.put(c3.getId(),c3);
    }

    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        log.debug("The method getById has been called on Customer Service component  with id: " + id);
        return Optional.of(customerMap.get(id));
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .version(1)
                .customerName(customer.getCustomerName())
                .build();
        customerMap.put(savedCustomer.getId(),savedCustomer);
        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO customer) {
        CustomerDTO savedCustomer = customerMap.get(id);
        if(savedCustomer!=null){
            savedCustomer = CustomerDTO.builder()
                    .id(customer.getId())
                    .customerName(customer.getCustomerName())
                    .lastModifiedDate(LocalDateTime.now())
                    .createdDate(customer.getCreatedDate())
                    .version(customer.getVersion()+1)
                    .customerName(customer.getCustomerName())
                    .build();
            log.debug("New Customer's version: "+savedCustomer.getVersion());
            customerMap.put(savedCustomer.getId(),savedCustomer);
            return Optional.of(savedCustomer);
        }
        return Optional.empty();
    }

    @DeleteMapping("{id}")
    @Override
    public void deleteCustomer(UUID id) {
        customerMap.remove(id);
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);
        if(existingCustomer!=null){
            if(StringUtils.hasText(customer.getCustomerName())){
                existingCustomer.setCustomerName(customer.getCustomerName());
            }
            existingCustomer.setVersion(existingCustomer.getVersion()+1);
            //updateBeer(existingBeer);
        }
    }
}
