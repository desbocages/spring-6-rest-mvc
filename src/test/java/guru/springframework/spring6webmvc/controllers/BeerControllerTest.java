package guru.springframework.spring6webmvc.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BeerControllerTest {
    @Autowired
    BeerController beerController;
    @Test
    void getBeerById() {
       // assertThrows(NotfoundException.class, (Executable)beerController.getBeerById(UUID.randomUUID()));
    }
}