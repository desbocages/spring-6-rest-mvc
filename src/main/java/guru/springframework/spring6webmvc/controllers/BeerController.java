package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.domain.BeerDTO;
import guru.springframework.spring6webmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@Slf4j
public class BeerController {
    public static final String BEER_BASE_PATH = "/api/v1/beer/";
    public static final String BEER_VAR_PATH_ID = BEER_BASE_PATH+"{beerId}";
    private final BeerService beerService;

    @GetMapping(BEER_BASE_PATH)
    public List<BeerDTO> listBeers(){
        return beerService.listBeers();
    }
    @GetMapping(BEER_VAR_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID id){
        log.debug("Collecting beer in Controller with id:"+id);
        return beerService.getBeerById(id).orElseThrow(NotfoundException::new);
    }

    @DeleteMapping(BEER_VAR_PATH_ID)
    public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID id){
        log.debug("Deleting beer in Controller with id:"+id);
        beerService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BEER_BASE_PATH)
    public ResponseEntity saveNewBeer(@RequestBody BeerDTO beer){
        BeerDTO b = beerService.saveNewBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",BEER_BASE_PATH+b.getId());
        return new ResponseEntity(headers, HttpStatusCode.valueOf(201));
    }

    @PatchMapping(BEER_VAR_PATH_ID)
   public ResponseEntity patchBeer(@PathVariable("beerId") UUID bId, @RequestBody BeerDTO beer){
        beerService.patchBeerById(bId,beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

    @PutMapping(BEER_VAR_PATH_ID)
    public ResponseEntity updateBeer(@PathVariable("beerId") UUID bId, @RequestBody BeerDTO beer){
        beerService.updateBeer(bId,beer).orElseThrow(NotfoundException::new);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
