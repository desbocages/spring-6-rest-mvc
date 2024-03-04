package guru.springframework.spring6webmvc.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Customer {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID",type = org.hibernate.id.uuid.UuidGenerator.class)
    @Column(length =36, columnDefinition = "varchar",nullable = false,updatable = false)
    private UUID id;
    @Column(length = 50)
    @NotNull
    @NotBlank
    private String customerName;
    @Version
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
