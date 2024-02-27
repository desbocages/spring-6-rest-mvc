package guru.springframework.spring6webmvc.domain.entities;

import guru.springframework.spring6webmvc.domain.BeerStyle;
import jakarta.persistence.*;
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
    private String beerName;
    @Version
    private Integer version;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
