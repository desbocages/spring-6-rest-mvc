package guru.springframework.spring6webmvc.domain.entities;

import guru.springframework.spring6webmvc.domain.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Beer {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID", type= UuidGenerator.class)
    @Column(columnDefinition = "varchar", nullable = false, updatable = false, length = 36)
    private UUID id;

    @Column(length = 50)
    @Size(max = 50)
    @NotNull
    @NotBlank
    private String beerName;
    @Version
    private Integer version;
    private BeerStyle beerStyle;
    @Column(length = 255)
    @NotNull
    @NotBlank
    private String upc;
    @NotNull
    @PositiveOrZero
    private Integer quantityOnHand;
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
