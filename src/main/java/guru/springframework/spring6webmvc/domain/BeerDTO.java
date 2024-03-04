package guru.springframework.spring6webmvc.domain;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BeerDTO {

    private UUID id;
    @NotNull
    @NotBlank
    private String beerName;

    @PositiveOrZero
    private Integer version;
    @NotNull
    private BeerStyle beerStyle;
    @NotNull
    @NotBlank
    private String upc;
    @NotNull
    @PositiveOrZero
    private Integer quantityOnHand;
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    @FutureOrPresent
    private LocalDateTime creationDate;
    @FutureOrPresent
    private LocalDateTime updateDate;

}
