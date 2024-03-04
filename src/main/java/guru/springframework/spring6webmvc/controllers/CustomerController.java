package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.domain.CustomerDTO;
import guru.springframework.spring6webmvc.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class CustomerController {

    public static final String CUSTOMER_BASE_PATH = "/api/v1/customer/";
    public static final String CUSTOMER_VAR_PATH_ID = CUSTOMER_BASE_PATH + "{cId}";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        log.debug("initializing the customer service component...");
        this.customerService = customerService;
    }


    @GetMapping(CUSTOMER_VAR_PATH_ID)
    public CustomerDTO getById(@PathVariable("cId") UUID id) {
        log.debug("Starting collecting customer by id [" + id.toString() + "]...");
        CustomerDTO c = customerService.getById(id).orElseThrow(NotfoundException::new);
        log.debug("Collected customer with id [" + id + "]..." + c);
        return c;
    }

    @GetMapping(CUSTOMER_BASE_PATH)
    public List<CustomerDTO> listCustomers() {
        log.debug("Customers list has been demanded...");
        List<CustomerDTO> customers = customerService.listCustomers();
        if (customers != null && !customers.isEmpty())
            log.debug("Returned a list of customers...Size: " + customers.size());
        else
            log.debug("No customer has been found.");
        return customers;
    }

    @PostMapping(CUSTOMER_BASE_PATH)
    public ResponseEntity saveNewCustomer(@Validated @RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_BASE_PATH + savedCustomer.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_VAR_PATH_ID)
    public ResponseEntity updateCustomer(@PathVariable("cId") UUID customerId, @RequestBody CustomerDTO customer) {
        if (customerService.updateCustomer(customerId, customer).isEmpty())
            throw new NotfoundException();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_VAR_PATH_ID)
    public ResponseEntity deleteCustomerById(@PathVariable("cId") UUID id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_VAR_PATH_ID)
    public ResponseEntity patchCustomerById(@PathVariable("cId") UUID customerId, @RequestBody CustomerDTO customer) {
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
