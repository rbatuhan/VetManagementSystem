package dev.patika.VetManagementSystem.core.config.modelMapper;

import org.modelmapper.ModelMapper;

public interface IModelMapperService {

    // İstek nesneleri için bir ModelMapper döndürür
    ModelMapper forRequest();

    // Yanıt nesneleri için bir ModelMapper döndürür
    ModelMapper forResponse();
}
