package dev.patika.VetManagementSystem.business.abstracts;

import dev.patika.VetManagementSystem.core.result.ResultData;
import dev.patika.VetManagementSystem.dto.request.Vaccine.VaccineSaveRequest;
import dev.patika.VetManagementSystem.dto.request.Vaccine.VaccineUpdateRequest;
import dev.patika.VetManagementSystem.dto.response.AnimalResponse;
import dev.patika.VetManagementSystem.dto.response.VaccineResponse;
import dev.patika.VetManagementSystem.entities.Vaccine;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IVaccineService {

    List<Vaccine> findByNameAndCode(String name, String code);

//      <<<<<<---------->>>>>
     //Yeni bir aşıyı kaydeder
    Vaccine save(Vaccine vaccine);

    // Belirli bir isme sahip aşıyı alır
    Vaccine get(String name);

    // Varolan bir aşıyı günceller
    Vaccine update(long id, @Valid VaccineUpdateRequest vaccineUpdateRequest);

    // Belirli bir kimlik numarasına sahip aşıyı siler
    Vaccine deleteById(long id);

    // Tüm aşıları alır
    List<Vaccine> findAll();

    // Değerlendirme Formu 22  <<<------->>>
    // Belirli bir isim, kod ve koruma bitiş tarihiyle koruma bitiş tarihinin geçerli olup olmadığını kontrol eder
    Optional<Vaccine> isProtectionEndDateValid(String name, String code, LocalDate protectionFinishDate);

    // Belirli bir kimlik numarasına sahip aşının mevcut olup olmadığını kontrol eder
    boolean existsById(long id);

    // Belirli bir hayvan kimliğine sahip aşıları alır
    List<Vaccine> getVaccinesByAnimalId(long animalId);

    // Belirli bir tarih aralığında gelecek aşıları alır
    List<Vaccine> getVaccinesWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate);

    // Belirli bir isim ve kodla eşleşen aşıyı bulur
    List<Vaccine> findByNameAndCodeAndAnimalId(String name, String code, Long animalId);



    // Belirli bir tarih aralığında gelecek aşıları olan hayvanları alır
    List<AnimalResponse> getAnimalsWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate);
}
