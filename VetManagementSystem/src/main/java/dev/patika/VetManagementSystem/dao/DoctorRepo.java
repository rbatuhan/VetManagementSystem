package dev.patika.VetManagementSystem.dao;

import dev.patika.VetManagementSystem.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {

    // Belirli bir e-posta adresine sahip doktorun varlığını kontrol eder
    boolean existsByMail(String mail);
}
