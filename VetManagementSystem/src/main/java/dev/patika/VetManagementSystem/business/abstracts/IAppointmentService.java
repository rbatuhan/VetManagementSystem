package dev.patika.VetManagementSystem.business.abstracts;


import dev.patika.VetManagementSystem.dto.request.Appointment.AppointmentUpdateRequest;
import dev.patika.VetManagementSystem.dto.response.AppointmentResponse;
import dev.patika.VetManagementSystem.entities.Appointment;
import dev.patika.VetManagementSystem.entities.Doctor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface IAppointmentService {

    // Yeni bir randevuyu kaydeder
    Appointment save(Appointment appointment);

    // Varolan bir randevuyu günceller
    Appointment update(long id, AppointmentUpdateRequest request);

    // Belirli bir kimlik numarasına sahip randevuyu siler
    Appointment delete(long id);

    // Tüm randevuları alır
    List<Appointment> findAll();

    // Belirli bir kimlik numarasına sahip doktoru alır
    Doctor getDoctorById(long id);

    // Belirli bir tarih aralığında ve doktor kimliğine sahip randevuları alır
    List<Appointment> getAppointmentsByDateAndDoctor(LocalDateTime startDate, LocalDateTime endDate, long doctorId);

    // Belirli bir tarih aralığında ve hayvan kimliğine sahip randevuları alır
    List<Appointment> getAppointmentsByDateAndAnimalId(LocalDateTime startDate, LocalDateTime endDate, long animalId);

}
