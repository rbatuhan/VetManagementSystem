package dev.patika.VetManagementSystem.dao;

import dev.patika.VetManagementSystem.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {

    // Belirli bir müşteri kimliğine sahip hayvanları bulur
    List<Animal> findByCustomerId(long customerId);

    // Belirli bir isimle hayvanları bulur
    List<Animal> findByName(String name);

    // Belirli bir kimlik numarasına sahip hayvanın varlığını kontrol eder
    boolean existsById(long id);

    // Belirli bir kimlik numarasına sahip hayvanı siler
    void deleteById(long id);

    // Belirli bir isim, tür ve ırkla eşleşen hayvanı bulur
    Optional<Animal> findByNameAndSpeciesAndBreed(String name, String species, String breed);
}