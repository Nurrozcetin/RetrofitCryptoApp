package com.example.retrofitcrypto.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitcrypto.R;
import com.example.retrofitcrypto.adapter.RecycleViewAdapter;
import com.example.retrofitcrypto.model.CryptoModel;
import com.example.retrofitcrypto.service.CryptoApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://raw.githubusercontent.com/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecycleViewAdapter recycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder().setLenient().create();
        recyclerView = findViewById(R.id.recyclerView);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        loadData();
    }

    private void loadData(){
        final CryptoApi cryptoApi = retrofit.create(CryptoApi.class);
        Call<List<CryptoModel>> call = cryptoApi.getData();
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if(response.isSuccessful()){
                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    //RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recycleViewAdapter = new RecycleViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recycleViewAdapter);
                    /*
                    for(CryptoModel cryptoModel : cryptoModels) {
                        System.out.println(cryptoModel.currency);
                        System.out.println(cryptoModel.price);
                    }*/
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}



