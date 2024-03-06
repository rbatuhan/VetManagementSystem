package dev.patika.VetManagementSystem.dto.request.Appointment;

import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Doctor;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDeleteRequest {
    @NotNull
    private Long id;

    private LocalDate appointmentDate;
    private Animal animal;
    private Doctor doctor;
}
