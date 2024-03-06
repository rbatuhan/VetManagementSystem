package dev.patika.VetManagementSystem.business.abstracts;

import dev.patika.VetManagementSystem.dto.request.Customer.CustomerSaveRequest;
import dev.patika.VetManagementSystem.entities.Customer;

import java.util.List;

public interface ICustomerService {

    // Yeni bir müşteriyi kaydeder
    Customer save(Customer customer);

    // Belirli bir isme sahip müşteriyi alır
    Customer get(String name);

    // Varolan bir müşteriyi günceller
    Customer update(long id, CustomerSaveRequest request);

    // Belirli bir kimlik numarasına sahip müşteriyi siler
    Customer deleteById(long id);

    // Tüm müşterileri alır
    List<Customer> findAll();

    // Belirli bir e-posta adresiyle bir müşterinin mevcut olup olmadığını kontrol eder
    boolean existsByMail(String mail);
}
