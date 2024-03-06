package dev.patika.VetManagementSystem.business.concretes;

import dev.patika.VetManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VetManagementSystem.core.exception.NotFoundException;
import dev.patika.VetManagementSystem.core.utiles.Msg;
import dev.patika.VetManagementSystem.dao.CustomerRepo;
import dev.patika.VetManagementSystem.dto.request.Customer.CustomerSaveRequest;
import dev.patika.VetManagementSystem.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerManager implements ICustomerService {
    private final CustomerRepo customerRepo;

    // Bağımlılıkları enjekte eden bir constructor
    public CustomerManager(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }
    @Override
    public Customer save(Customer customer) {
        // Müşteriyi kaydeder
        return this.customerRepo.save(customer);
    }

    @Override
    public Customer get(String name) {
        // İsimle müşteri arar ve bulursa döndürür
        return this.customerRepo.findByName(name);
    }

    public Customer update(long id, CustomerSaveRequest request) {
        // Belirli bir kimlik numarasına sahip müşteriyi günceller
        Optional<Customer> optionalCustomer = customerRepo.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setName(request.getName());
            customer.setPhone(request.getPhone());
            customer.setMail(request.getMail());
            customer.setAddress(request.getAddress());
            customer.setCity(request.getCity());
            return customerRepo.save(customer);
        } else {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
    }
    public Customer deleteById(long id) {
        // Belirli bir kimlik numarasına sahip müşteriyi siler
        Optional<Customer> customerFromDb = customerRepo.findById(id);
        if (customerFromDb.isPresent()) {
            Customer deletedCustomer = customerFromDb.get();
            customerRepo.delete(deletedCustomer);
            return deletedCustomer;
        } else {
            throw new NotFoundException(id + " id'li müşteri sistemde bulunamadı!!!");
        }
    }

    public List<Customer> findAll(){
        // Tüm müşterileri alır
        return this.customerRepo.findAll();
    }

    @Override
    public boolean existsByMail(String mail) {
        // Belirli bir e-posta adresi ile müşterinin varlığını kontrol eder
        return customerRepo.existsByMail(mail);
    }
}
