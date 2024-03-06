package dev.patika.VetManagementSystem.core.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultData <T> extends Result {
    private T data; // Sonuç verisi
    public ResultData(boolean status, String msg, String code, T data) {
        super(status, msg, code); // Üst sınıfın yapıcı metodunu çağırır
        this.data=data; // Sonuç verisini ayarlar
    }
}
