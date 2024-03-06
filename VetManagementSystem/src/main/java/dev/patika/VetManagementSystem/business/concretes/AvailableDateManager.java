package dev.patika.VetManagementSystem.business.concretes;

import dev.patika.VetManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VetManagementSystem.core.exception.NotFoundException;
import dev.patika.VetManagementSystem.dao.AvailableDateRepo;
import dev.patika.VetManagementSystem.dao.DoctorRepo;
import dev.patika.VetManagementSystem.entities.AvailableDate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class AvailableDateManager implements IAvailableDateService {
    private final DoctorRepo doctorRepo;

    private final AvailableDateRepo availableDateRepo;

    // Bağımlılıkları enjekte eden bir constructor
    public AvailableDateManager(DoctorRepo doctorRepo, AvailableDateRepo availableDateRepo) {
        this.doctorRepo = doctorRepo;
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    public boolean existsByDoctorIdAndAvailable_date(long doctorId, LocalDate available_date) {
        // Belirli bir doktorun belirli bir tarih için müsaitlik durumunu kontrol eder
        return availableDateRepo.existsByDoctorIdAndAvailable_date(doctorId, available_date);
    }

    @Override
    public AvailableDate deletebyDateId(AvailableDate availableDate) {
        // Belirli bir kimlik numarasına sahip müsaitlik kaydını siler
        Optional<AvailableDate> dateFromDb = availableDateRepo.findById(availableDate.getId());
        if (dateFromDb.isPresent()) {
            AvailableDate deleteDate = dateFromDb.get();
            availableDateRepo.delete(deleteDate);
            return deleteDate;
        } else {
            throw new NotFoundException(availableDate.getId() + " id'li doktor sistemde bulunamadı!!!");
        }
    }

    @Override
    public List<AvailableDate> getByDoctorId(long doctorId) {
        // Belirli bir doktora ait müsaitlik tarihlerini alır
        return availableDateRepo.findByDoctorId(doctorId);
    }

    @Override
    public List<AvailableDate> findAll() {
        // Tüm müsaitlik tarihlerini alır
        return availableDateRepo.findAll();
    }

    @Override
    public AvailableDate get(long id) {
        // Belirli bir kimlik numarasına sahip müsaitlik kaydını alır
        return availableDateRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id + " id'li müsaitlik kaydı bulunamadı"));
    }

    @Override
    public AvailableDate save(AvailableDate saveDate) {
        // Müsaitlik kaydını kaydeder
        return this.availableDateRepo.save(saveDate);
    }

    @Override
    public AvailableDate update(long id, AvailableDate availableDate) {
        // Belirli bir kimlik numarasına sahip müsaitlik kaydını günceller
        if (availableDateRepo.existsById(id)) {
            availableDate.setId(id);
            return availableDateRepo.save(availableDate);
        } else {
            throw new NotFoundException("Hayvan bulunamadı!");
        }
    }
}
