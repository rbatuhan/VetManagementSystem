package dev.patika.VetManagementSystem.dao;

import dev.patika.VetManagementSystem.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    // Belirli bir doktorun belirli bir tarih aralığında randevularını bulur
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findByDoctorIdAndDateTimeBetween(
            @Param("doctorId") long doctorId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // Belirli bir hayvanın belirli bir tarih aralığında randevularını bulur
    @Query("SELECT a FROM Appointment a WHERE a.animal.id = :animalId AND a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findByAnimalIdAndDateTimeBetween(
            @Param("animalId") long animalId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // Belirli bir doktorun belirli bir tarih ve saatte çakışan randevularını bulur
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDate = :dateTime")
    List<Appointment> findConflictingAppointmentsForDoctor(
            @Param("doctorId") long doctorId,
            @Param("dateTime") LocalDateTime dateTime
    );
}
