package dev.patika.VetManagementSystem.dto.response;

import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Customer;
import dev.patika.VetManagementSystem.entities.Vaccine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Animal.GENDER gender;
    private String color;
    private LocalDate dateOfBirth;
    private Customer customer;
}
