package guru.springframework.spring6webmvc.repositories;

import guru.springframework.spring6webmvc.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
