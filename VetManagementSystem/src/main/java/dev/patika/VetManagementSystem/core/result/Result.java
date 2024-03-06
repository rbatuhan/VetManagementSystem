package dev.patika.VetManagementSystem.core.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private boolean status; // İşlem başarılıysa true, değilse false
    private String msg; // Sonuç mesajı
    private String code; // Sonuç kodu

    public Result(boolean status, String msg, String code) {
        this.status = status; // İşlem başarısı
        this.msg = msg; // Sonuç mesajı
        this.code = code; // Sonuç kodu
    }
}
