package guru.springframework.spring6webmvc.domain;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
    private UUID id;
    @NotNull
    @NotBlank
    private String customerName;
    @PositiveOrZero
    private Integer version;
    @FutureOrPresent
    private LocalDateTime createdDate;
    @FutureOrPresent
    private LocalDateTime lastModifiedDate;
}
