package dev.patika.VetManagementSystem.dto.request.Animal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDeleteRequest {

    @NotNull
    private Long id;
}
