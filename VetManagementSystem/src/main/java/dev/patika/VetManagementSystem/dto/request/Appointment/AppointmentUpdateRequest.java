package dev.patika.VetManagementSystem.dto.request.Appointment;

import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Doctor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    @NotNull(message = "Randevu boş bırakılamaz!")
    private LocalDateTime appointmentDate;

//    @NotNull
//    private Animal animal;
//
//    @NotNull
//    private Doctor doctor;
}
