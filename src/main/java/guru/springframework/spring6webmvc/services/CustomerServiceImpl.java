package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID,Customer> customerMap;
    public CustomerServiceImpl() {
        log.debug("Initializing the CustomerServiceImpl class...");
        customerMap = new HashMap<>();
        Customer c1 = Customer.builder()
                .customerName("Yakam")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        Customer c2 = Customer.builder()
                .customerName("Yale")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        Customer c3 = Customer.builder()
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
    public Customer getById(UUID id) {
        log.debug("The method getById has been called on Customer Service component  with id: " + id);
        return customerMap.get(id);
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }
}
