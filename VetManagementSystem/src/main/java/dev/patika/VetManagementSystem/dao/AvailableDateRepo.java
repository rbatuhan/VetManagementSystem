package dev.patika.VetManagementSystem.dao;


import dev.patika.VetManagementSystem.dto.response.AvailableDateResponse;
import dev.patika.VetManagementSystem.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Long> {

    // Yeni bir müsait tarih kaydı oluşturur
    AvailableDateResponse save(AvailableDateResponse availableDateResponse);

    // Belirli bir doktorun belirli bir tarihte mevcut olup olmadığını kontrol eder
    @Query("SELECT COUNT(ad) > 0 FROM AvailableDate ad WHERE ad.doctor.id = :doctorId AND ad.available_date = :available_date")
    boolean existsByDoctorIdAndAvailable_date(long doctorId, LocalDate available_date);

    // id numarasına göre müsait tarihi bulur
    Optional<AvailableDate> findById(long id);

    // Doktor id'sine göre  tüm müsait tarihleri listeler
    List<AvailableDate> findByDoctorId(long doctorId);
}
