package dev.patika.VetManagementSystem.core.config.modelMapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelManagerService implements IModelMapperService {
    private final ModelMapper modelMapper; // ModelMapper nesnesi

    @Autowired
    public ModelManagerService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper; // ModelMapper'ı enjekte eder
    }

    // İstek nesneleri için bir ModelMapper döndürür
    @Override
    public ModelMapper forRequest() {
        // ModelMapper yapılandırmasını ayarlar: Belirsizlik yok, eşleştirme stratejisi STANDARD
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper; // ModelMapper nesnesini döndürür
    }

    // Yanıt nesneleri için bir ModelMapper döndürür
    @Override
    public ModelMapper forResponse() {
        // ModelMapper yapılandırmasını ayarlar: Belirsizlik yok, eşleştirme stratejisi LOOSE
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.LOOSE);
        return this.modelMapper; // ModelMapper nesnesini döndürür
    }
}
