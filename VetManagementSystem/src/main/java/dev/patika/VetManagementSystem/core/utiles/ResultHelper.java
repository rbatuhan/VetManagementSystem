package dev.patika.VetManagementSystem.core.utiles;

import dev.patika.VetManagementSystem.core.result.ResultData;
import dev.patika.VetManagementSystem.dto.response.AnimalResponse;

public class ResultHelper {

    // Başarıyla oluşturulmuş bir sonuç döndürür
    public static <T>ResultData<T> created(T data){
        return new ResultData<>(true, Msg.CREATED,"201",data);
    }

    // Veri doğrulama hatasıyla oluşturulmuş bir sonuç döndürür
    public static <T>ResultData<T> validateError(T data){
        return new ResultData<>(false, Msg.VALIDATE_ERROR,"400",data);
    }

    // Başarılı bir sonuç döndürür
    public static <T>ResultData<T> success(T data){
        return new ResultData<>(true, Msg.OK,"200",data);
    }

    // Veri bulunamadığına dair bir sonuç döndürür
    public static <T>ResultData<T> notFound(T data){
        return new ResultData<>(false, Msg.NOT_FOUND ,"404",data);
    }

    // Kullanılabilir olmayan bir sonuç döndürür
    public static <T>ResultData<T> notAvailable(T data){
        return new ResultData<>(false, Msg.NOT_AVB ,"404",data);
    }

    // Veriyle birlikte başarısız bir sonuç döndürür
    public static <T>ResultData<T> failWithData( T data) {
        return new ResultData<>(false, Msg.FOUND, "404", data);
    }

    // Hayvan silme işlemi başarılı olduğunda bir sonuç döndürür
    public static ResultData<AnimalResponse> deleted(AnimalResponse map) {
        return new ResultData<>(true, Msg.OK,"200",map);
    }

    // Başarısız bir sonuç döndürür
    public static <T>ResultData<T> fail(T data) {
        return new ResultData<>(false, Msg.FAIL, "404", data);
    }

    public static <T> ResultData<T> vaccineProtectionDateNotArrived() {
        return new ResultData<>(false, Msg.VACCINE_PROTECTION_DATE_NOT_ARRIVED, "422", null);
    }

    public static <T> ResultData<T> failData() {
        return new ResultData<>(false, Msg.FOUND, "404", null);
    }
}
