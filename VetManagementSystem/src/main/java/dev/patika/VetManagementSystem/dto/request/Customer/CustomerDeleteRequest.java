package dev.patika.VetManagementSystem.dto.request.Customer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDeleteRequest {
    @NotNull
    private Long id;
}
