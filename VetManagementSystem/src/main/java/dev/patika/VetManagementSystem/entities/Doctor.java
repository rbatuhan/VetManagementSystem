package dev.patika.VetManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "doctor_name", length = 255)
    @NotNull
    private String name;

    @Column(name = "doctor_phone", length = 255)
    @NotNull
    private String phone;

    @Column(name = "doctor_ mail", length = 255)
    @Email
    @NotNull
    private String mail;

    @Column(name = "doctor_address", length = 255)
    @NotNull
    private String address;

    @Column(name = "doctor_city", length = 255)
    @NotNull
    private String city;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Appointment> appointmentList;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<AvailableDate> availableDateList;
}
