package dev.patika.VetManagementSystem.api;

import dev.patika.VetManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VetManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VetManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VetManagementSystem.core.result.ResultData;
import dev.patika.VetManagementSystem.core.utiles.ResultHelper;
import dev.patika.VetManagementSystem.dto.request.Animal.AnimalUpdateRequest;
import dev.patika.VetManagementSystem.dto.request.Appointment.AppointmentSaveRequest;
import dev.patika.VetManagementSystem.dto.request.Appointment.AppointmentUpdateRequest;
import dev.patika.VetManagementSystem.dto.request.Vaccine.VaccineUpdateRequest;
import dev.patika.VetManagementSystem.dto.response.AppointmentResponse;
import dev.patika.VetManagementSystem.dto.response.CustomerResponse;
import dev.patika.VetManagementSystem.dto.response.VaccineResponse;
import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Appointment;
import dev.patika.VetManagementSystem.entities.Customer;
import dev.patika.VetManagementSystem.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final IDoctorService doctorService;
    private final IAppointmentService appointmentService;
    private final IModelMapperService modelMapper;

    public AppointmentController(IDoctorService doctorService, IAppointmentService appointmentService, IModelMapperService modelMapper) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }

    // Değerlendirme Formu 17 - Randevu Kayıt Etme.
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@RequestBody @Valid AppointmentSaveRequest request) {
        // Randevu için gelen talep verileri
        LocalDateTime appointmentDate = request.getAppointmentDate();
        Long doctorId = request.getDoctor().getId(); // veya direkt olarak Doctor nesnesini kullanabilirsiniz
        // Değerlendirme Formu 18 - Randevu Zaman Kontrolü
        // Doktorun uygun olduğu tarihleri kontrol et
        AppointmentResponse response = null;
        if (!doctorService.isDoctorAvailable(doctorId, appointmentDate)) {
            return ResultHelper.notAvailable(response);
            // Doktor uygun değilse hata mesajı döndür
        } else {
            // Doktor uygunsa işlemi devam ettir
            Appointment appointmentToSave = modelMapper.forRequest().map(request, Appointment.class);
            Appointment savedAppointment = appointmentService.save(appointmentToSave);
            response = modelMapper.forResponse().map(savedAppointment, AppointmentResponse.class);
            return ResultHelper.created(response);
        }
    }
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<Appointment> update(@PathVariable long id, @RequestBody @Valid AppointmentUpdateRequest appointmentUpdateRequest) {
        // Randevuyu günceller
        Appointment updatedAppointment = appointmentService.update(id, appointmentUpdateRequest);
        return ResultHelper.success(updatedAppointment);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> findAll() {
        // Tüm Randevuları getir
        List<Appointment> appointments = appointmentService.findAll();
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(appointmentResponses);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> delete(@PathVariable("id") long id) {
        // Randevu sil
        Appointment deleteAppointment = this.appointmentService.delete(id);
        AppointmentResponse appointmentResponse = null;
        if (deleteAppointment != null) {
            appointmentResponse = this.modelMapper.forResponse().map(deleteAppointment, AppointmentResponse.class);
            return ResultHelper.success(appointmentResponse);
        } else {
            return ResultHelper.notFound(appointmentResponse);
        }
    }

    // Değerlendirme Formu 20 - Randevular Girilen Doktor İd ye ve Tarihe Göre Filtreleme
    @GetMapping("/get/doctor-date/{doctorId}/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> findAllByDoctorAndDate(
            @PathVariable long doctorId,
            @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate) {
        // Belirli bir doktorun belirli bir tarihte yapılacak randevuları getir
        List<AppointmentResponse> responseList = appointmentService.getAppointmentsByDateAndDoctor(
                        startDate,
                        endDate,
                        doctorId)
                .stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(responseList);
    }

    // Değerlendirme Formu 19 - Randevular Girilen Hayvan İd ye ve Tarihe Göre Filtreleme
    @GetMapping("/get/animal-date/{animalId}/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<Appointment >> findAllByAnimalAndDate(
            @PathVariable long animalId,
            @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate)  {
        // Belirli bir hayvanın belirli bir tarihte yapılacak randevuları getir
        List<Appointment> responseList = appointmentService.getAppointmentsByDateAndAnimalId(startDate,endDate,animalId)
                .stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, Appointment.class))
                .collect(Collectors.toList());
        return ResultHelper.success(responseList);
    }
}
