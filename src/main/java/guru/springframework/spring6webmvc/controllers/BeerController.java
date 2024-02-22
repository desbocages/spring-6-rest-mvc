package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.domain.Beer;
import guru.springframework.spring6webmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
@Slf4j
public class BeerController {
    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }
    @RequestMapping("/{beerId}")
    public Beer getBeerById(@PathVariable("beerId") UUID id){
        log.debug("Collecting beer in Controller with id:"+id);
        return beerService.getBeerById(id);
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID id){
        log.debug("Deleting beer in Controller with id:"+id);

        beerService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody Beer beer){
        Beer b = beerService.saveNewBeer(beer);
        return ResponseEntity.status(201)
                .header("Location","/api/v1/beer/"+b.getId())
                .body(b);//.build();
    }

    @PatchMapping("{bId}")
   public ResponseEntity patchBeer(@PathVariable("bId") UUID bId, @RequestBody Beer beer){
        beerService.patchBeerById(bId,beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
   }
}
