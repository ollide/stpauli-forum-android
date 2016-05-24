package org.ollide.stpauliforum.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("login.php")
    Call<Void> login(@FieldMap Map<String, String> fields);

}
