package dev.patika.VetManagementSystem.business.concretes;

import dev.patika.VetManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VetManagementSystem.core.exception.NotFoundException;
import dev.patika.VetManagementSystem.core.utiles.Msg;
import dev.patika.VetManagementSystem.dao.AnimalRepo;
import dev.patika.VetManagementSystem.dao.CustomerRepo;
import dev.patika.VetManagementSystem.dto.request.Animal.AnimalUpdateRequest;
import dev.patika.VetManagementSystem.dto.request.Customer.CustomerSaveRequest;
import dev.patika.VetManagementSystem.dto.response.AnimalResponse;
import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalManager implements IAnimalService {
    private final CustomerRepo customerrepo;
    private final AnimalRepo animalRepo;

    // Bağımlılıkları enjekte eden bir constructor
    public AnimalManager( CustomerRepo customerrepo, AnimalRepo animalRepo) {
        this.customerrepo = customerrepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public Animal save(Animal animal) {
        return animalRepo.save(animal);
    }

    @Override
    public Animal update(long id, AnimalUpdateRequest request) {
        // Belirli bir kimlik numarasına sahip müşteriyi günceller
        Optional<Animal> optionalCustomer = animalRepo.findById(id);
        if (optionalCustomer.isPresent()) {
            Animal animal = optionalCustomer.get();
            animal.setName(request.getName());
            animal.setBreed(request.getBreed());
            animal.setColor(request.getColor());
            animal.setDateOfBirth(request.getDateOfBirth());
            animal.setGender(request.getGender());
            animal.setSpecies(request.getSpecies());
            return animalRepo.save(animal);
        } else {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
    }

    @Override
    public Animal getById(long id) {
        // Belirli bir kimlik numarasına sahip hayvanı alır, bulunamazsa NotFound hatası fırlatır
        return animalRepo.findById(id).orElseThrow(() -> new NotFoundException("Hayvan bulunamadı!"));
    }
    @Override
    public List<Animal> getByName(String name) {
        // Belirli bir isme sahip hayvanları alır
        return animalRepo.findByName(name);
    }

    @Override
    public List<Animal> getAll() {
        // Tüm hayvanları alır
        return animalRepo.findAll();
    }

    @Override
    public List<AnimalResponse> getAllByCustomerId(long customerId) {
        // Belirli bir müşteri kimliğine sahip tüm hayvanları alır ve AnimalResponse nesnelerine dönüştürür
        return animalRepo.findByCustomerId(customerId).stream()
                .map(animal -> {
                    AnimalResponse animalResponse = new AnimalResponse();
                    animalResponse.setId(animal.getId());
                    animalResponse.setName(animal.getName());
                    animalResponse.setSpecies(animal.getSpecies());
                    animalResponse.setBreed(animal.getBreed());
                    animalResponse.setGender(animal.getGender());
                    return animalResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Customer getCustomerByCustomerId(long id) {
        // Belirli bir müşteri kimliğine sahip müşteriyi alır, bulunamazsa null döner
        Optional<Customer> customerOptional = customerrepo.findById(id);

        return customerOptional.orElse(null);
    }


    @Override
    public Animal deleteById(long id) {
        // Belirli bir kimlik numarasına sahip hayvan var mı kontrol eder, yoksa NotFound hatası fırlatır
        if (animalRepo.existsById(id)) {
            animalRepo.deleteById(id);
        } else {
            throw new NotFoundException("Hayvan bulunamadı!");
        }
        return null;
    }

    @Override
    public Optional<Animal> findByNameAndSpeciesAndBreed(String name, String species, String breed) {
        // Belirli bir isim, tür ve ırkla eşleşen hayvanı bulur
        return animalRepo.findByNameAndSpeciesAndBreed(name, species, breed);
    }
}
