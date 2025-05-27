package com.example.newsapp.category;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.MainNews;
import com.example.newsapp.R;
import com.example.newsapp.adapter.Adapter;
import com.example.newsapp.apiutilities.ApiUtilities;
import com.example.newsapp.model.Model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {
    String api = "91134f8b676a4f5d8a60b185d2d72dd7";
    private RecyclerView recyclerViewOfHome;
    ArrayList<Model> modelArrayList;
    Adapter adapter;
    private ProgressBar progressBar;
    String country = "us";
    private boolean isDataLoaded = false;  // Flag to check if data is already loaded

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        recyclerViewOfHome = view.findViewById(R.id.recyclerViewOfHome);
        progressBar = view.findViewById(R.id.progress_horizontal);
        modelArrayList = new ArrayList<>();
        recyclerViewOfHome.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext(), modelArrayList);
        recyclerViewOfHome.setAdapter(adapter);
        if (!isDataLoaded) {
            findNews();
            progressBar.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public void findNews(){
        ApiUtilities.getApiInterface().getNews(country, 100, api).enqueue(new Callback<MainNews>() {
            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {
                if (response.isSuccessful()) {
                    modelArrayList.addAll(response.body().getArticles());
                    progressBar.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle unsuccessful response (e.g., 403)
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.e("HomeFragment", "Error: " + response.code() + " - " + response.message());
                    Toast.makeText(getContext(), "Failed to load news: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainNews> call, Throwable t) {
                // Handle failure, such as network issues
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("HomeFragment", "Failure: " + t.getMessage());
                Toast.makeText(getContext(), "Network failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

