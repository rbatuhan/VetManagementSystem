package dev.patika.VetManagementSystem.dto.request.Customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {
    @NotNull(message = "Müşteri adı boş bırakılamaz!")
    private String name;

    @NotNull(message = "Müşteri numarası boş bırakılamaz!")
    private String phone;

    @Email(message = "Geçerli bir mail adresi giriniz!")
    @NotNull(message = "Mail boş bırakılamaz!")
    private String mail;

    @NotNull(message = "Adres boş bırakılamaz!")
    private String address;

    @NotNull(message = "Şehir boş bırakılamaz!")
    private String city;
}
