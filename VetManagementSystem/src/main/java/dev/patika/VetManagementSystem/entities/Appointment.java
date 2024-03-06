package dev.patika.VetManagementSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id", columnDefinition = "serial")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Column(name = "appointment_date", length = 255)
    private LocalDateTime appointmentDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_animalId", referencedColumnName = "animal_id")
    private Animal animal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_doctorId", referencedColumnName = "doctor_id")
    private Doctor doctor;

}
