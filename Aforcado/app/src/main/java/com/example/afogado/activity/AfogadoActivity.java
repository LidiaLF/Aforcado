package com.example.afogado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.afogado.R;
import com.example.afogado.database.DataBaseHelper;
import com.example.afogado.databinding.ActivityAfogadoBinding;

public class AfogadoActivity extends AppCompatActivity {
    ActivityAfogadoBinding binding;
   // private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAfogadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
/*
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/

        DataBaseHelper dbHelper = DataBaseHelper.getInstance(AfogadoActivity.this);
        try {
            dbHelper.createDataBase();
        } catch (Exception e) {
            Log.e(DataBaseHelper.class.getSimpleName(), "Cant create or get database ");
        }

        binding.btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfogadoActivity.this, GameActivity.class);
                startActivity(i);
            }
        });

        binding.btSkins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfogadoActivity.this, SkinsActivity.class);
                startActivity(i);
            }
        });
    }
}