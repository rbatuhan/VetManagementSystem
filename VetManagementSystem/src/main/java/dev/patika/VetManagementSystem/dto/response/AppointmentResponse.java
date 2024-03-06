package dev.patika.VetManagementSystem.dto.response;

import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentDate;
    private Animal animal;
    private Doctor doctor;
}
