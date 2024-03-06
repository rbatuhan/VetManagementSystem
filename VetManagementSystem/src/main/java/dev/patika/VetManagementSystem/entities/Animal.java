package dev.patika.VetManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.id.factory.spi.GeneratorDefinitionResolver;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "animal_name", nullable = false, length = 255)
    @NotNull
    private String name;

    @Column(name = "animal_species", nullable = false, length = 255)
    @NotNull
    private String species;

    @Column(name = "animal_breed", nullable = false, length = 255)
    @NotNull
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_gender", nullable = false)
    private GENDER gender;

    @Column(name = "animal_color", nullable = false, length = 255)
    @NotNull
    private String color;

    @Temporal(TemporalType.DATE)
    @Column(name = "animal_dateOfBirth")
    @NotNull
    private LocalDate dateOfBirth;

    public enum GENDER {
        MALE,
        FEMALE
    }

    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Vaccine> vaccineList;

    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Appointment> appointmentList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_customerId", referencedColumnName = "customer_id")
    private Customer customer;
}
