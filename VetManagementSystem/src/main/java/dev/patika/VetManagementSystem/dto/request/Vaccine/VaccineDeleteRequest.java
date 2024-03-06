package dev.patika.VetManagementSystem.dto.request.Vaccine;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineDeleteRequest {
    @NotNull
    private Long id;
}
