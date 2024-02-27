package guru.springframework.spring6webmvc;

import guru.springframework.spring6webmvc.domain.BeerDTO;
import guru.springframework.spring6webmvc.domain.BeerStyle;
import guru.springframework.spring6webmvc.domain.CustomerDTO;
import guru.springframework.spring6webmvc.mappers.BeerMapper;
import guru.springframework.spring6webmvc.mappers.BeerMapperImpl;
import guru.springframework.spring6webmvc.mappers.CustomerMapper;
import guru.springframework.spring6webmvc.mappers.CustomerMapperImpl;
import guru.springframework.spring6webmvc.repositories.BeerRepository;
import guru.springframework.spring6webmvc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class BootstrapData implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {

        if (beerRepository.count() == 0) {
            BeerDTO beer1 = BeerDTO.builder()
                    .beerName("King")
                    .beerStyle(BeerStyle.MALTED)
                    .upc("nothing")
                    .price(new BigDecimal(650))
                    .quantityOnHand(55)
                    .creationDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            BeerDTO beer2 = BeerDTO.builder()
                    .beerName("33 Export")
                    .beerStyle(BeerStyle.MALTED)
                    .upc("nothing")
                    .price(new BigDecimal(650))
                    .quantityOnHand(75)
                    .creationDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            BeerDTO beer3 = BeerDTO.builder()
                    .beerName("Booster Cola")
                    .beerStyle(BeerStyle.WHISKIED)
                    .upc("nothing")
                    .price(new BigDecimal(650))
                    .quantityOnHand(10)
                    .creationDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            Stream<BeerDTO> beerStream = Stream.of(beer1, beer3, beer2);
            BeerMapper beerMapper = new BeerMapperImpl();
            beerRepository.saveAll(beerStream.map(e -> beerMapper.beerDTOToBeer(e)).toList());

        }
        if (customerRepository.count() == 0) {
            CustomerDTO c1 = CustomerDTO.builder()
                    .customerName("Yakam")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            CustomerDTO c2 = CustomerDTO.builder()
                    .customerName("Yale")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            CustomerDTO c3 = CustomerDTO.builder()
                    .customerName("Yakoubou")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            Stream<CustomerDTO> customerStream = Stream.of(c1, c2, c3);
            CustomerMapper customerMapper = new CustomerMapperImpl();
            customerRepository.saveAll(customerStream.map(e -> customerMapper.customerDTOToCustomer(e)).toList());

        }
    }
}
