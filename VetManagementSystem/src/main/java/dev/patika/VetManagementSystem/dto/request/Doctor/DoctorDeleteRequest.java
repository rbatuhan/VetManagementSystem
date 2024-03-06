package dev.patika.VetManagementSystem.dto.request.Doctor;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDeleteRequest {
    @NotNull
    private Long id;
}
