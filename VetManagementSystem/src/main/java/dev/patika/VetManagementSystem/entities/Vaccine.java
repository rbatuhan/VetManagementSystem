package dev.patika.VetManagementSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "vaccines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "vaccine_name", nullable = false, length = 255)
    @NotNull
    private String name;

    @Column(name = "vaccine_code", nullable = false, length = 255)
    @NotNull
    private String code;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "vaccine_strtDate", nullable = false)
    private LocalDate protectionStartDate;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "vaccine_fnshDate", nullable = false)
    private LocalDate protectionFinishDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vaccine_animalId", referencedColumnName = "animal_id")
    private Animal animal;

}
