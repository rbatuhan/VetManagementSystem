package dev.patika.VetManagementSystem.business.concretes;

import dev.patika.VetManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VetManagementSystem.core.exception.NotFoundException;
import dev.patika.VetManagementSystem.core.utiles.Msg;
import dev.patika.VetManagementSystem.dao.DoctorRepo;
import dev.patika.VetManagementSystem.dto.request.Doctor.DoctorSaveRequest;
import dev.patika.VetManagementSystem.entities.Appointment;
import dev.patika.VetManagementSystem.entities.AvailableDate;
import dev.patika.VetManagementSystem.entities.Doctor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorManager implements IDoctorService {
    private final DoctorRepo doctorRepo;

    public DoctorManager(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }
    @Override
    public Doctor save(Doctor doctor) {
        // Doktoru kaydeder
        return this.doctorRepo.save(doctor);
    }

    @Override
    public Doctor get(long id) {
        // Belirli bir kimlik numarasına sahip doktoru döndürür
        return this.doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    public Doctor update(long id, DoctorSaveRequest request) {
        // Belirli bir kimlik numarasına sahip doktoru günceller
        Optional<Doctor> optionalDoctor = doctorRepo.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setName(request.getName());
            doctor.setPhone(request.getPhone());
            doctor.setMail(request.getMail());
            doctor.setAddress(request.getAddress());
            doctor.setCity(request.getCity());
            return doctorRepo.save(doctor);
        } else {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
    }
    public Doctor deleteById(long id) {
        // Belirli bir kimlik numarasına sahip doktoru siler
        Optional<Doctor> doctorFromDb = doctorRepo.findById(id);
        if (doctorFromDb.isPresent()) {
            Doctor deletedDoctor = doctorFromDb.get();
            doctorRepo.delete(deletedDoctor);
            return deletedDoctor;
        } else {
            throw new NotFoundException(id + " id'li doktor sistemde bulunamadı!!!");
        }
    }
    public List<Doctor> findAll(){
        // Tüm doktorları döndürür
        return this.doctorRepo.findAll();
    }

    @Override
    public boolean existByMail(String mail) {
        // Belirli bir e-posta adresi ile doktorun varlığını kontrol eder
        return doctorRepo.existsByMail(mail);
    }

    public boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentDateTime) {
        // Belirli bir doktorun belirli bir zamanda uygun olup olmadığını kontrol eder
        Optional<Doctor> optionalDoctor = doctorRepo.findById(doctorId);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();

            //Uygun tarihlerin kontrolü
            List<AvailableDate> availableDates = doctor.getAvailableDateList();
            for (AvailableDate availableDate : availableDates) {
                LocalDate availableDateOnly = availableDate.getAvailable_date();

                //Tarih kontrolü
                if (appointmentDateTime.toLocalDate().isEqual(availableDateOnly)) {

                    //Uygun tarih varsa saati kontrol et
                    return isAppointmentTimeAvailable(doctor, appointmentDateTime.toLocalTime());
                }
            }
            return false;
        } else {
            throw new NotFoundException("Doktor bulunamadı!");
        }
    }

    private boolean isAppointmentTimeAvailable(Doctor doctor, LocalTime appointmentTime) {
        // Belirli bir doktorun belirli bir saatte randevu alabilecek durumda olup olmadığını kontrol eder

        List<Appointment> doctorAppointments = doctor.getAppointmentList(); // Doktorun randevu saatleri
        for (Appointment appointment : doctorAppointments) {
            LocalDateTime existingAppointmentDateTime = appointment.getAppointmentDate();

            //Saat konrolü
            if (existingAppointmentDateTime.toLocalTime().equals(appointmentTime)) {
                System.out.println("doktor dolu");
                return false;
            }
        }
        System.out.println("doktor boş");
        return true;
    }
}
