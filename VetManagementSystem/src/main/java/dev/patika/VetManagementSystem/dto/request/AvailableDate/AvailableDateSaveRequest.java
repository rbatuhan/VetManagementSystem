package dev.patika.VetManagementSystem.dto.request.AvailableDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.patika.VetManagementSystem.entities.Doctor;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateSaveRequest {

    @NotNull(message = "Müsait tarih boş bırakılamaz!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate available_date;

    @NotNull
    private Doctor doctor;
}
