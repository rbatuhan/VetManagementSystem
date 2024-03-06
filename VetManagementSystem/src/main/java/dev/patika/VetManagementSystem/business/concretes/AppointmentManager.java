package dev.patika.VetManagementSystem.business.concretes;

import dev.patika.VetManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VetManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VetManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VetManagementSystem.core.exception.NotFoundException;
import dev.patika.VetManagementSystem.core.utiles.Msg;
import dev.patika.VetManagementSystem.dao.AppointmentRepo;
import dev.patika.VetManagementSystem.dto.request.Animal.AnimalUpdateRequest;
import dev.patika.VetManagementSystem.dto.request.Appointment.AppointmentUpdateRequest;
import dev.patika.VetManagementSystem.dto.response.AppointmentResponse;
import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Appointment;
import dev.patika.VetManagementSystem.entities.Doctor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentManager implements IAppointmentService {
    private final IModelMapperService modelMapper;

    private final AppointmentRepo appointmentRepo;
    private final IDoctorService doctorService;

    // Bağımlılıkları enjekte eden bir constructor
    public AppointmentManager(IModelMapperService modelMapper, AppointmentRepo appointmentRepo, IDoctorService doctorService) {
        this.modelMapper = modelMapper;
        this.appointmentRepo = appointmentRepo;
        this.doctorService = doctorService;
    }

    @Override
    public Appointment save(Appointment appointment) {
        // Çakışan randevuları kontrol eder ve yeni randevuyu kaydeder
        checkConflictingAppointments(appointment);
        if (appointment.getDoctor() != null) {
            return appointmentRepo.save(appointment);
        } else {
            throw new RuntimeException("Doktor bilgisi boş olamaz!");
        }
    }

    @Override
    public Appointment update(long id, AppointmentUpdateRequest request) {
        // Belirli bir kimlik numarasına sahip randevuyu günceller
        Optional<Appointment> optionalAppointment = appointmentRepo.findById(id);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setAppointmentDate(request.getAppointmentDate());
            return appointmentRepo.save(appointment);
        } else {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
    }


    @Override
    public Appointment delete(long id) {
        // Belirli bir kimlik numarasına sahip randevuyu siler
        Optional<Appointment> appointmentFromDb = appointmentRepo.findById(id);
        if (appointmentFromDb.isPresent()) {
            Appointment deletedAppointment = appointmentFromDb.get();
            appointmentRepo.delete(deletedAppointment);
            return deletedAppointment;
        } else {
            throw new NotFoundException(id + " id'li randevu sistemde bulunamadı!!!");
        }
    }

    @Override
    public List<Appointment> findAll() {
        // Tüm randevuları alır
        return appointmentRepo.findAll();
    }

    @Override
    public Doctor getDoctorById(long id) {
        // Belirli bir doktor kimliğine sahip doktoru alır
        return doctorService.get(id);
    }

    @Override
    public List<Appointment> getAppointmentsByDateAndDoctor(LocalDateTime startDate, LocalDateTime endDate, long doctorId) {
        // Belirli bir tarih aralığında belirli bir doktora ait randevuları alır
        Doctor doctor = doctorService.get(doctorId);
        if (doctor == null) {
            throw new RuntimeException("Doktor bulunamadı!");
        }
        return appointmentRepo.findByDoctorIdAndDateTimeBetween(
                doctor.getId(),
                startDate,
                endDate
        );
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDateAndAnimalId(LocalDateTime startDate, LocalDateTime endDate, long animalId) {
        // Belirli bir tarih aralığında belirli bir hayvana ait randevuları alır
        List<Appointment> appointments = appointmentRepo.findByAnimalIdAndDateTimeBetween(
                animalId,
                startDate,
                endDate
        );

        return appointments.stream()
                .filter(appointment -> startDate.isBefore(appointment.getAppointmentDate()) && endDate.isAfter(appointment.getAppointmentDate()))
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
    }

    // Çakışan randevuları kontrol eder
    private void checkConflictingAppointments(Appointment newAppointment) {
        if (newAppointment.getDoctor() != null) {
            List<Appointment> conflictingAppointments = appointmentRepo.findConflictingAppointmentsForDoctor(
                    newAppointment.getDoctor().getId(),
                    newAppointment.getAppointmentDate()
            );

            if (!conflictingAppointments.isEmpty()) {
                throw new RuntimeException("Doktorun bu saatte başka bir randevusu bulunmaktadır!");
            }
        } else {
            throw new RuntimeException("Doktor bilgisi boş olamaz!");
        }
    }
}
