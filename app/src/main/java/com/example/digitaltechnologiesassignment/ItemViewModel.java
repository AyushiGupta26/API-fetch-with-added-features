package com.example.digitaltechnologiesassignment;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ItemViewModel extends ViewModel {
    //this is the data that we will fetch asynchronously
    private MutableLiveData<String> stringresponse;

    //we will call this method to get the data
    public LiveData<String> getString() {
        //if the list is null
        if (stringresponse == null) {
            stringresponse = new MutableLiveData<String>();
            //we will load it asynchronously from server in this method
            loadString();
        }

        //finally we will return the list
        return stringresponse;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadString() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("onSuccess", response.body());
                        stringresponse.setValue(response.body());
                    } else {
                        Log.d("onEmptyResponse", "Returned empty response");
//                        Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("onFailure",t.getMessage());
            }
        });
    }
}
