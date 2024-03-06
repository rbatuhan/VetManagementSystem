package dev.patika.VetManagementSystem.business.abstracts;

import dev.patika.VetManagementSystem.core.result.ResultData;
import dev.patika.VetManagementSystem.entities.AvailableDate;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService {

    // Belirli bir doktor kimliği ve müsait tarih için bir eşleşme var mı kontrol eder
    boolean existsByDoctorIdAndAvailable_date(long doctorId, LocalDate availableDate);

    // Belirli bir tarih kimliğine sahip müsait bir tarihi siler
    AvailableDate deletebyDateId(AvailableDate date);

    // Belirli bir doktor kimliğine sahip müsait tarihleri alır
    List<AvailableDate> getByDoctorId(long doctorId);

    // Tüm müsait tarihleri alır
    List<AvailableDate> findAll();

    // Belirli bir tarih kimliğine sahip müsait bir tarihi alır
    AvailableDate get(long id);

    // Yeni bir müsait tarih kaydeder
    AvailableDate save(AvailableDate saveDate);

    // Varolan bir müsait tarihi günceller
    AvailableDate update(long id, AvailableDate availableDate);
}
