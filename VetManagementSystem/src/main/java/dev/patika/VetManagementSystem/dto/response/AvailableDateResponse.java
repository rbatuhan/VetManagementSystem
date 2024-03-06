package dev.patika.VetManagementSystem.dto.response;

import dev.patika.VetManagementSystem.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateResponse {
    private Long id;
    private LocalDate available_date;
    private Doctor doctor;
}
