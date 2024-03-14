package dev.patika.VetManagementSystem.api;

import dev.patika.VetManagementSystem.business.abstracts.IVaccineService;
import dev.patika.VetManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VetManagementSystem.core.result.ResultData;
import dev.patika.VetManagementSystem.core.utiles.ResultHelper;
import dev.patika.VetManagementSystem.dao.VaccineRepo;
import dev.patika.VetManagementSystem.dto.request.Vaccine.VaccineSaveRequest;
import dev.patika.VetManagementSystem.dto.request.Vaccine.VaccineUpdateRequest;
import dev.patika.VetManagementSystem.dto.response.VaccineResponse;
import dev.patika.VetManagementSystem.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {

    private final IVaccineService vaccineService;
    private final IModelMapperService modelMapper;
    private final VaccineRepo vaccineRepo;

    public VaccineController(IVaccineService vaccineService, IModelMapperService modelMapper, VaccineRepo vaccineRepo) {
        this.vaccineService = vaccineService;
        this.modelMapper = modelMapper;
        this.vaccineRepo = vaccineRepo;
    }

//      <<<------->>>>
     //Değerlendirme Formu 21 - Aşı Kayıt Etme
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@RequestBody @Valid VaccineSaveRequest request) {
        // Aşıyı kaydet
        List<Vaccine> vaccineList=vaccineService.findByNameAndCodeAndAnimalId(request.getName(),request.getCode(), request.getAnimal().getId());
        for (Vaccine vaccine: vaccineList){
            if (vaccine.getProtectionFinishDate().isAfter(request.getProtectionStartDate())){
                return ResultHelper.vaccineProtectionDateNotArrived();
            }
        }
            // Aşıyı kaydet
            Vaccine saveVaccine = this.modelMapper.forRequest().map(request, Vaccine.class);
            this.vaccineService.save(saveVaccine);
            VaccineResponse saved = this.modelMapper.forResponse().map(saveVaccine, VaccineResponse.class);
            // Başarıyla kaydedildiğine dair bir cevap döndür
            return ResultHelper.created(saved);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@PathVariable("id") long id, @RequestBody @Valid VaccineUpdateRequest vaccineUpdateRequest) {
        // Aşıyı güncelle
        List<Vaccine> existingVaccineOptional = vaccineService.findByNameAndCode(vaccineUpdateRequest.getName(), vaccineUpdateRequest.getCode());

        VaccineResponse vaccineResponse = null;
        if (!existingVaccineOptional.isEmpty()) {
            return ResultHelper.fail(vaccineResponse);
        } else {
            Vaccine updateVaccine = this.vaccineService.update(id, vaccineUpdateRequest);
            vaccineResponse = this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class);
            return ResultHelper.success(vaccineResponse);
        }
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> delete(@PathVariable("id") long id) {
        // Aşıyı sil
        Vaccine deleteVaccine = this.vaccineService.deleteById(id);
        VaccineResponse vaccineResponse = null;
        if (deleteVaccine == null) {
            vaccineResponse = this.modelMapper.forResponse().map(deleteVaccine, VaccineResponse.class);
            return ResultHelper.notFound(vaccineResponse);
        } else {
            return ResultHelper.success(vaccineResponse);
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> findAll() {
        // Tüm aşıları getir
        List<Vaccine> vaccines = vaccineService.findAll();
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }

    // Değerlendirme Formu 24 - Girilen Animal İd ye göre tüm aşıları getirme.
    @GetMapping("/animal/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesByAnimalId(@PathVariable("animalId") long animalId) {
        // Hayvan ID'sine göre aşıları getir
        List<Vaccine> vaccines = vaccineService.getVaccinesByAnimalId(animalId);
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }

    // Değerlendirme Formu 23 - Girilen Tarih Aralığına Göre Aşı Listeleme
    @GetMapping("/upcoming/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getUpcomingVaccinations(
            @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        // Yaklaşan aşıları getir
        List<Vaccine> vaccines = vaccineService.getVaccinesWithUpcomingVaccinations(startDate, endDate);
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }
}
