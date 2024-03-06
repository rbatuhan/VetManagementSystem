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
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "customer_name", nullable = false, length = 255)
    @NotNull
    private String name;

    @Column(name = "customer_phone", nullable = false, length = 255)
    @NotNull
    private String phone;

    @Column(name = "customer_mail", nullable = false, length = 255)
    @Email
    @NotNull
    private String mail;

    @Column(name = "customer_address", nullable = false, length = 255)
    @NotNull
    private String address;

    @Column(name = "customer_city", nullable = false, length = 255)
    @NotNull
    private String city;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Animal> animalList;
}
