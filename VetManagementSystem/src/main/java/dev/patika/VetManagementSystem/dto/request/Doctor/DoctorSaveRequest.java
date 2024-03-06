package dev.patika.VetManagementSystem.dto.request.Doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {
    @NotNull(message = "Doktor adı boş bırakılamaz!")
    private String name;

    @NotNull(message = "Telefon numarası boş bırakılamaz!")
    private String phone;

    @Email(message = "Geçerli bir email adresi giriniz.")
    @NotNull(message = "Email boş bırakılamaz!")
    private String mail;

    @NotNull(message = "Adres boş bırakılamaz!")
    private String address;

    @NotNull(message = "Şehir boş bırakılamaz!")
    private String city;
}
