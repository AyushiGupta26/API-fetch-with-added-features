package com.example.digitaltechnologiesassignment;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    String JSONURL = "http://kekizadmin.com/kekiz_api/";
    @GET("actions.php?action=get_cakes&category=27")
    Call<String> getString();

}
