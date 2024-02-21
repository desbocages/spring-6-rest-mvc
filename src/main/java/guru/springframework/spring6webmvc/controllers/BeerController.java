package guru.springframework.spring6webmvc.controllers;

import guru.springframework.spring6webmvc.domain.Beer;
import guru.springframework.spring6webmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
