package dev.patika.VetManagementSystem.business.abstracts;


import dev.patika.VetManagementSystem.dto.request.Doctor.DoctorSaveRequest;
import dev.patika.VetManagementSystem.entities.Doctor;

import java.time.LocalDateTime;
import java.util.List;

public interface IDoctorService {

    // Yeni bir doktoru kaydeder
    Doctor save(Doctor doctor);

    // Belirli bir kimlik numarasına sahip doktoru alır
    Doctor get(long id);

    // Varolan bir doktoru günceller
    Doctor update(long id, DoctorSaveRequest request);

    // Belirli bir kimlik numarasına sahip doktoru siler
    Doctor deleteById(long id);

    // Tüm doktorları alır
    List<Doctor> findAll();

    // Belirli bir e-posta adresiyle bir doktorun mevcut olup olmadığını kontrol eder
    boolean existByMail(String doctorMail);

    // Belirli bir doktor kimliği ve randevu tarihi için doktorun uygun olup olmadığını kontrol eder
    boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentDate);

}
