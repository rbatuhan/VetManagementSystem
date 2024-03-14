package dev.patika.VetManagementSystem.business.concretes;

import dev.patika.VetManagementSystem.business.abstracts.IVaccineService;
import dev.patika.VetManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VetManagementSystem.core.exception.NotFoundException;
import dev.patika.VetManagementSystem.core.result.ResultData;
import dev.patika.VetManagementSystem.core.utiles.Msg;
import dev.patika.VetManagementSystem.core.utiles.ResultHelper;
import dev.patika.VetManagementSystem.dao.VaccineRepo;
import dev.patika.VetManagementSystem.dto.request.Vaccine.VaccineSaveRequest;
import dev.patika.VetManagementSystem.dto.request.Vaccine.VaccineUpdateRequest;
import dev.patika.VetManagementSystem.dto.response.AnimalResponse;
import dev.patika.VetManagementSystem.dto.response.VaccineResponse;
import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Vaccine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VaccineManager implements IVaccineService {
    private final VaccineRepo vaccineRepo;
    private final EntityManager entityManager;

    public VaccineManager(VaccineRepo vaccineRepo, EntityManager entityManager) {
        this.vaccineRepo = vaccineRepo;
        this.entityManager = entityManager;
    }


//      <<<<-------->>>>>
    @Override
    public Vaccine save(Vaccine vaccine) {
        // Aşıyı kaydeder
        return this.vaccineRepo.save(vaccine);
    }


    @Override
    public Vaccine get(String name) {
        // Belirli bir isme sahip aşıyı döndürür
        return this.vaccineRepo.findByName(name);
    }

    @Override
    public Vaccine update(long id, @Valid VaccineUpdateRequest update) {
        // Belirli bir kimlik numarasına sahip aşıyı günceller
        Optional<Vaccine> optionalVaccine = this.vaccineRepo.findById(id);
        if (optionalVaccine.isPresent()) {
            Vaccine vaccine = optionalVaccine.get();
            vaccine.setName(update.getName());
            vaccine.setCode(update.getCode());
            vaccine.setProtectionStartDate(update.getProtectionStartDate());
            vaccine.setProtectionFinishDate(update.getProtectionFinishDate());
            return vaccineRepo.save(vaccine);
        } else {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
    }

    @Override
    public Vaccine deleteById(long id) {
        // Belirli bir kimlik numarasına sahip aşıyı siler
        Optional<Vaccine> optionalVaccine = this.vaccineRepo.findById(id);
        if (optionalVaccine.isPresent()) {
            Vaccine deleteVaccine = optionalVaccine.get();
            vaccineRepo.delete(deleteVaccine);
            return deleteVaccine;
        } else {
            throw new NotFoundException(id + " id'li ilaç sistemde bulunamadı!!!");
        }
    }

    @Override
    public List<Vaccine> findAll() {
        // Tüm aşıları döndürür
        return this.vaccineRepo.findAll();
    }

    @Override
    public Optional<Vaccine> isProtectionEndDateValid(String name, String code, LocalDate protectionFinishDate) {
        // Koruma bitiş tarihi geçerli olan bir aşıyı döndürür
        return vaccineRepo.findByNameAndCodeAndProtectionFinishDateAfter(name, code, protectionFinishDate);
    }

    @Override
    public boolean existsById(long id) {
        // Belirli bir kimlik numarasına sahip aşının var olup olmadığını kontrol eder
        return vaccineRepo.existsById(id);
    }

    @Override
    public List<Vaccine> getVaccinesByAnimalId(long animalId) {
        // Belirli bir hayvan kimliğine sahip aşıları döndürür
        return vaccineRepo.findByAnimalId(animalId);
    }

    @Override
    public List<Vaccine> getVaccinesWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate) {
        // Yaklaşan aşıları döndürür
        return vaccineRepo.findVaccinesWithUpcomingVaccinations(startDate, endDate);
    }

    @Override
    public List<Vaccine> findByNameAndCodeAndAnimalId(String name, String code, Long animalId) {
        // Belirli bir isme ve koda sahip aşıyı döndürür
        return vaccineRepo.findByNameAndCodeAndAnimalId(name, code, animalId);
    }

    @Override
    public List<Vaccine> findByNameAndCode(String name, String code) {
        // Belirli bir isme ve koda sahip aşıyı döndürür
        return vaccineRepo.findByNameAndCode(name, code);
    }

    @Override
    public List<AnimalResponse> getAnimalsWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate) {
        // Yaklaşan aşılara sahip hayvanları döndürür

        List<Animal> animals = vaccineRepo.findAnimalsWithUpcomingVaccinations(startDate, endDate);
        return animals.stream()
                .map(animal -> {
                    AnimalResponse animalResponse = new AnimalResponse();
                    animalResponse.getId();
                    animalResponse.getName();
                    animalResponse.getSpecies();
                    animalResponse.getBreed();
                    animalResponse.getGender();
                    return animalResponse;
                })
                .collect(Collectors.toList());
    }
}
