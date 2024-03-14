package dev.patika.VetManagementSystem.api;

import dev.patika.VetManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VetManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VetManagementSystem.core.result.ResultData;
import dev.patika.VetManagementSystem.core.utiles.ResultHelper;
import dev.patika.VetManagementSystem.dao.AnimalRepo;
import dev.patika.VetManagementSystem.dto.request.Animal.AnimalSaveRequest;
import dev.patika.VetManagementSystem.dto.request.Animal.AnimalUpdateRequest;
import dev.patika.VetManagementSystem.dto.response.AnimalResponse;
import dev.patika.VetManagementSystem.dto.response.AvailableDateResponse;
import dev.patika.VetManagementSystem.entities.Animal;
import dev.patika.VetManagementSystem.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {

    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;

    public AnimalController(IAnimalService animalService, IModelMapperService modelMapper) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }

    // // Değerlendirme Formu 12 - Hayvan Kayıt Etme.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<Animal> save(@RequestBody @Valid Animal animal) {
        Optional<Animal> isAnimalExist = animalService.findByNameAndSpeciesAndBreed(animal.getName(), animal.getSpecies(), animal.getBreed());
        if (isAnimalExist.isPresent()) {
            return ResultHelper.failWithData(animal);
        } else {
            Customer customer = animalService.getCustomerByCustomerId(animal.getCustomer().getId());
            Animal savedAnimal = animalService.save(animal);
            savedAnimal.setCustomer(customer);
            return ResultHelper.created(savedAnimal);
        }
    }

    //id ye göre animal update etme
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<Animal> update(@PathVariable long id, @RequestBody @Valid AnimalUpdateRequest animalUpdateRequest) {
        Animal updatedAnimal = animalService.update(id, animalUpdateRequest);
        return ResultHelper.success(updatedAnimal);
    }

    // id ye göre animal listeleme Hayvanları kaydetme, bilgilerini güncelleme, görüntüleme ve silme
    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<Animal> getById(@PathVariable long id) {
        Animal animal = animalService.getById(id);
        return ResultHelper.success(animal);
    }

    // Değerlendirme Formu 13 - Hayvanları İsme Göre Filtreleme.
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<Animal>> getByName(@PathVariable String name) {
        List<Animal> animals = animalService.getByName(name);
        return ResultHelper.success(animals);
    }

    // tüm hayvanları listeleme
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<Animal>> getAll() {
        List<Animal> animals = animalService.getAll();
        return ResultHelper.success(animals);
    }

    // Değerlendirme Formu 14
    // Hayvan sahibinin sistemde kayıtlı tüm hayvanlarını görüntülemek için API end point'ini oluşturmak.
    @GetMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAllByCustomerId(@PathVariable long customerId) {
        List<AnimalResponse> animals = animalService.getAllByCustomerId(customerId);
        return ResultHelper.success(animals);
    }

    // id ye göre animal silme
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> deleteById(@PathVariable ("id") long id) {
        Animal deleteAnimal = animalService.deleteById(id);
        AnimalResponse animalResponse = null;
        if (deleteAnimal != null) {
            animalResponse = this.modelMapper.forResponse().map(deleteAnimal, AnimalResponse.class);
            return ResultHelper.success(animalResponse);
        } else {
            return ResultHelper.deleted(animalResponse);
        }
    }
}
