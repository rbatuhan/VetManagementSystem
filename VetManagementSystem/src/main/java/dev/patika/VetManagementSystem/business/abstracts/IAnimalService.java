package dev.patika.VetManagementSystem.business.abstracts;

import dev.patika.VetManagementSystem.dto.request.Animal.AnimalUpdateRequest;
import dev.patika.VetManagementSystem.dto.response.AnimalResponse;
import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface IAnimalService {

    // Yeni bir hayvanı kaydeder
    Animal save(Animal animal);

    // Varolan bir hayvanı günceller
    Animal update(long id, AnimalUpdateRequest request);

    // Belirli bir kimlik numarasına sahip hayvanı alır
    Animal getById(long id);

    // Belirli bir isimle eşleşen hayvanları alır
    List<Animal> getByName(String name);

    // Tüm hayvanları alır
    List<Animal> getAll();

    // Belirli bir müşteri kimliğine sahip hayvanları alır
    List<AnimalResponse> getAllByCustomerId(long customerId);

    // Belirli bir müşteri kimliğine sahip müşteriyi alır
    Customer getCustomerByCustomerId(long id);

    // Belirli bir kimlik numarasına sahip hayvanı siler
    Animal deleteById(long id);

    // Belirli bir isim, tür ve ırkla eşleşen hayvanı bulur
    Optional<Animal> findByNameAndSpeciesAndBreed(String name, String species, String breed);

}
