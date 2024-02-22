package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.domain.Customer;
import guru.springframework.spring6webmvc.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        log.debug("initializing the customer service component...");
        this.customerService = customerService;
    }

    @RequestMapping(value = "/{cId}",method = RequestMethod.GET)
    public Customer getById(@PathVariable("cId") UUID id){
        log.debug("Starting collecting customer by id ["+id.toString()+"]...");
        Customer c = customerService.getById(id);
        log.debug("Collected customer with id ["+id.toString()+"]..."+c+"");
        return c;
    }

    @RequestMapping("/")
    public List<Customer> listCustomers(){
        log.debug("Customers list has been demanded...");
        List<Customer> customers = customerService.listCustomers();
        if(customers!=null && !customers.isEmpty())
        log.debug("Returned a list of customers...Size: "+customers.size());
        else
            log.debug("No customer has been found.");
        return customers;
    }
    @PostMapping
    public ResponseEntity saveNewCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/customer/"+savedCustomer.getId());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @PutMapping("{cId}")
    public ResponseEntity updateCustomer(@PathVariable("cId") UUID customerId,@RequestBody Customer customer){
        Customer savedCustomer = customerService.getById(customerId);
        savedCustomer.setCustomerName(customer.getCustomerName());
        savedCustomer.setVersion(customer.getVersion());
        Customer updated = customerService.updateCustomer(savedCustomer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/customer/"+savedCustomer.getId());
        return new ResponseEntity(headers,HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("{cId}")
    public ResponseEntity deleteCustomerById(@PathVariable("cId") UUID id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
