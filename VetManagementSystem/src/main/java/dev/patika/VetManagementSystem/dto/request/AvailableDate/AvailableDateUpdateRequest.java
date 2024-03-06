package dev.patika.VetManagementSystem.dto.request.AvailableDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateUpdateRequest {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate availableDate;

}
