package dev.patika.VetManagementSystem.core.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message); // Üst sınıfın yapıcı metodunu çağırır ve istisna mesajını ayarlar
    }
}
