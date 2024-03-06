package dev.patika.VetManagementSystem.dto.request.Animal;

import dev.patika.VetManagementSystem.entities.Animal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateRequest {
    @NotNull(message = "İsim boş bırakılamaz!")
    private String name;

    @NotNull(message = "Tür boş bırakılamaz!")
    private String species;

    @NotNull(message = "Irk boş bırakılamaz!")
    private String breed;

    @NotNull(message = "Cinsiyet boş bırakılamaz!")
    private Animal.GENDER gender;

    @NotNull(message = "Renk boş bırakılamaz!")
    private String color;

    @NotNull(message = "Doğum tarihi boş bırakılamaz!")
    private LocalDate dateOfBirth;
}
