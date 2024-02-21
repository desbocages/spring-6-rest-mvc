package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.domain.Customer;
import guru.springframework.spring6webmvc.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
