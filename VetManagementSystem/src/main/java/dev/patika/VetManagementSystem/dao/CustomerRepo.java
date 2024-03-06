package dev.patika.VetManagementSystem.dao;

import dev.patika.VetManagementSystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    // Belirli bir isimle müşteri arar
    Customer findByName(String name);

    // Belirli bir e-posta adresine sahip müşterinin varlığını kontrol eder
    boolean existsByMail(String mail);
}
