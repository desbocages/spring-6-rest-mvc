package guru.springframework.spring6webmvc.repositories;

import guru.springframework.spring6webmvc.domain.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
