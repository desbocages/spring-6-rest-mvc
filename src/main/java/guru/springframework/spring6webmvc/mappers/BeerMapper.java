package guru.springframework.spring6webmvc.mappers;

import guru.springframework.spring6webmvc.domain.BeerDTO;
import guru.springframework.spring6webmvc.domain.entities.Beer;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDTOToBeer(BeerDTO dto);
    BeerDTO beerToBeerDTO(Beer entity);
}
