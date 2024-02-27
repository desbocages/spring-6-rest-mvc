package guru.springframework.spring6webmvc.services;

import guru.springframework.spring6webmvc.domain.BeerDTO;
import guru.springframework.spring6webmvc.domain.entities.Beer;
import guru.springframework.spring6webmvc.mappers.BeerMapper;
import guru.springframework.spring6webmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class BeerServiceJPAImpl implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;
    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(
                beerMapper.beerToBeerDTO(
                        beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        beer.setCreationDate(LocalDateTime.now());
        beer.setUpdateDate(LocalDateTime.now());
        Beer b = beerRepository.save(beerMapper.beerDTOToBeer(beer));
        return beerMapper.beerToBeerDTO(b);
    }

    @Override
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> reference = new AtomicReference<>();
        beerRepository.findById(id).ifPresentOrElse(found->{
                found.setBeerName(beer.getBeerName());
                found.setBeerStyle(beer.getBeerStyle());
                found.setPrice(beer.getPrice());
                found.setUpc(beer.getUpc());
                reference.set(Optional.of(beerMapper.beerToBeerDTO(beerRepository.save(found))));
                },
                ()->{
                    reference.set(Optional.empty());
        });
        return reference.get();
    }

    @Override
    public void deleteById(UUID id) {
        beerRepository.deleteById(id);
    }

    @Override
    public void patchBeerById(UUID id, BeerDTO beer1) {

    }
}
