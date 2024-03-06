package dev.patika.VetManagementSystem.api;

import dev.patika.VetManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VetManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VetManagementSystem.core.result.ResultData;
import dev.patika.VetManagementSystem.core.utiles.ResultHelper;
import dev.patika.VetManagementSystem.dto.request.AvailableDate.AvailableDateSaveRequest;
import dev.patika.VetManagementSystem.dto.response.AvailableDateResponse;
import dev.patika.VetManagementSystem.entities.AvailableDate;
import dev.patika.VetManagementSystem.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/availableDates")
public class AvailableDateController {

    private final IAvailableDateService availableDateService;
    private final IModelMapperService modelMapper;

    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService modelMapper) {
        this.availableDateService = availableDateService;
        this.modelMapper = modelMapper;
    }

    // Değerlendirme Formu 16 - Doktor Müsait Günü Kayıt Etme.
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest date) {
        // Doktorun aynı tarihte uygunluğunu kontrol et
        if (availableDateService.existsByDoctorIdAndAvailable_date(date.getDoctor().getId(), date.getAvailable_date())) {
            return ResultHelper.failWithData(null);
        }
        // Yeni uygun tarih kaydını oluştur
        AvailableDate saveDate = this.modelMapper.forRequest().map(date, AvailableDate.class);

        Doctor doctor = new Doctor();
        doctor.setId(date.getDoctor().getId());
        saveDate.setDoctor(doctor);
        this.availableDateService.save(saveDate);

        return ResultHelper.created(this.modelMapper.forRequest().map(date, AvailableDateResponse.class));
    }
    @PutMapping("/update/{id}") // id'ye göre veriyi güncelle
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDate> update(@PathVariable long id, @RequestBody @Valid AvailableDate availableDate) {
        // Uygun tarihi güncelle
        AvailableDate updateDate = availableDateService.update(id, availableDate);
        return ResultHelper.success(updateDate);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> delete(@PathVariable("id") AvailableDate id) {
        // Uygun tarihi sil
        AvailableDate deleteDate = this.availableDateService.deletebyDateId(id);
        AvailableDateResponse dateResponse = null;
        if (deleteDate != null) {
            dateResponse = this.modelMapper.forResponse().map(deleteDate, AvailableDateResponse.class);
            return ResultHelper.success(dateResponse);
        } else {
            return ResultHelper.notFound(dateResponse);
        }
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id") long id) {
        // Belirli bir uygun tarihi getir
        AvailableDate availableDate = this.availableDateService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));
    }

    @GetMapping("/doctor/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDate>> getByDoctorId(@PathVariable("doctorId") long doctorId) {
        // Belirli bir doktorun tüm uygun tarihlerini getir
        List<AvailableDate> availableDates = this.availableDateService.getByDoctorId(doctorId);
        return ResultHelper.success(availableDates);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDate>> findAll() {
        // Tüm uygun tarihleri getir
        List<AvailableDate> availableDates = this.availableDateService.findAll();
        return ResultHelper.success(availableDates);
    }
}
