package dev.patika.VetManagementSystem.api;

import dev.patika.VetManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VetManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VetManagementSystem.core.result.ResultData;
import dev.patika.VetManagementSystem.core.utiles.ResultHelper;
import dev.patika.VetManagementSystem.dto.request.Customer.CustomerSaveRequest;
import dev.patika.VetManagementSystem.dto.response.CustomerResponse;
import dev.patika.VetManagementSystem.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final ICustomerService customerService;
    private final IModelMapperService modelMapper;

    public CustomerController(ICustomerService customerService, IModelMapperService modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    // Değerlendirme Formu 10 - Hayvan Sahibi ( Customer ) Kayıt Etme.
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<Customer> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {
        // Müşteri kaydını kontrol et
        String customerMail = customerSaveRequest.getMail();
        Customer saveCustomer = null;
        if (customerService.existsByMail(customerMail)) {
            return ResultHelper.failWithData(null);
        }
        // Müşteriyi kaydet
        saveCustomer = this.modelMapper.forRequest().map(customerSaveRequest, Customer.class);
        this.customerService.save(saveCustomer);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveCustomer, Customer.class));
    }

    // Değerlendirme Formu 11 - Hayvan Sahibi ( Customer ) İsme Göre Filtreleme.
    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("name") String name){
        // Belirli bir müşteriyi getir
        Customer customer = this.customerService.get(name);
        return ResultHelper.success(this.modelMapper.forResponse().map(customer,CustomerResponse.class));
    }
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@PathVariable("id") long id, @RequestBody @Valid CustomerSaveRequest customerSaveRequest) {
        // Müşteriyi güncelle
        Customer updatedCustomer = this.customerService.update(id, customerSaveRequest);
        CustomerResponse customerResponse = null;
        if (updatedCustomer != null) {
            customerResponse = this.modelMapper.forResponse().map(updatedCustomer, CustomerResponse.class);
            return ResultHelper.success(customerResponse);
        } else {
            return ResultHelper.notFound(customerResponse);
        }
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> delete(@PathVariable("id") long id) {
        // Müşteriyi sil
        Customer deleteCustomer = this.customerService.deleteById(id);
        CustomerResponse customerResponse = null;
        if (deleteCustomer != null) {
            customerResponse = this.modelMapper.forResponse().map(deleteCustomer, CustomerResponse.class);
            return ResultHelper.success(customerResponse);
        } else {
            return ResultHelper.notFound(customerResponse);
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> findAll() {
        // Tüm müşterileri getir
        List<Customer> customers = customerService.findAll();
        List<CustomerResponse> customerResponses = customers.stream()
                .map(customer -> modelMapper.forResponse().map(customer, CustomerResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(customerResponses);
    }

}
