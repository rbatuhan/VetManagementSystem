package dev.patika.VetManagementSystem.dto.request.AvailableDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateDeleteRequest {
    @NotNull
    private Long id;
}
