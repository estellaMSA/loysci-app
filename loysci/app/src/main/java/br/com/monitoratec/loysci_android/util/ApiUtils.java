package br.com.monitoratec.loysci_android.util;

import com.google.gson.Gson;

import br.com.monitoratec.loysci_android.model.ResponseError;
import retrofit2.Response;

public final class ApiUtils {
    public static <T> ResponseError getError(Response<T> response) {
        try {
            return new Gson().fromJson(response.errorBody().string(), ResponseError.class);
        } catch (Exception e) {
            return new ResponseError(response.code(), e.getMessage());
        }
    }
}