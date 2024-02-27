package guru.springframework.spring6webmvc.mappers;

import guru.springframework.spring6webmvc.domain.CustomerDTO;
import guru.springframework.spring6webmvc.domain.entities.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDTOToCustomer(CustomerDTO dto);
    CustomerDTO customerToCustomerDTO(Customer entity);
}
