package dev.patika.VetManagementSystem.dao;


import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {

    // Belirli bir isim ve kodla eşleşen aşıyı bulur
    List<Vaccine> findByNameAndCode(String name, String code);

    // Belirli bir koruma bitiş tarihinden sonra belirli bir kodla aşı arar
    Vaccine findByCodeAndProtectionFinishDateAfter(String code, LocalDate protectionFinishDate);

    // Belirli bir isimle aşı arar
    Vaccine findByName(String name);

    // Belirli bir hayvan kimliğine göre aşıları listeler
    List<Vaccine> findByAnimalId(long animalId);

    // Belirli bir tarih aralığında koruması bitecek aşıların yapıldığı hayvanları bulur
    @Query("SELECT v FROM Vaccine v WHERE v.protectionStartDate >= :startDate AND v.protectionFinishDate <= :endDate")
    List<Animal> findAnimalsWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate);

    // Belirli bir tarih aralığında koruması bitecek aşıları bulur
    @Query("SELECT v FROM Vaccine v WHERE v.protectionFinishDate BETWEEN :startDate AND :endDate")
    List<Vaccine> findVaccinesWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate);

    // Belirli bir isim ve kodla ve animalId eşleşen aşıyı bulur
    List<Vaccine> findByNameAndCodeAndAnimalId(String name, String code, Long animalId);

    // Belirli bir isim, kod ve koruma bitiş tarihinden sonra eşleşen aşıyı bulur
    Optional<Vaccine> findByNameAndCodeAndProtectionFinishDateAfter(String name, String code, LocalDate protectionFinishDate);

}
