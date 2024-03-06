package dev.patika.VetManagementSystem.core.config.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    // ModelMapper bean'ini oluşturur ve yapılandırır
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper(); // Yeni bir ModelMapper nesnesi döndürür
    }
}
