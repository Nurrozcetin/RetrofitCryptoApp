package com.example.retrofitcrypto.service;

import com.example.retrofitcrypto.model.CryptoModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoApi {
    //get, post, update, delete

    //BASE URL -> ww.example.com
    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    Call<List<CryptoModel>> getData();

}
