package dev.patika.VetManagementSystem.dto.request.Vaccine;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineUpdateRequest {

    @NotNull(message = "Aşı adı boş veya null olamaz!!")
    private String name;

    @NotNull(message = "Aşı kodu boş veya null olamaz!!")
    private String code;

    @NotNull(message = "Aşı koruma başlangıç tarihi boş bırakılamaz!")
    private LocalDate protectionStartDate;

    @NotNull(message = "Aşı koruma bitiş tarihi boş bırakılamaz!")
    private LocalDate protectionFinishDate;
}
