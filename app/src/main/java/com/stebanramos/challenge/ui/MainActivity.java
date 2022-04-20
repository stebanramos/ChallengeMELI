package com.stebanramos.challenge.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stebanramos.challenge.BuildConfig;
import com.stebanramos.challenge.databinding.ActivityMainBinding;
import com.stebanramos.challenge.utilies.Preferences;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    private EditText etSearch;
    private Button btnSearch;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        configView();
    }

    private void configView(){
        etSearch = binding.etSearch;
        btnSearch = binding.btnSearch;

        btnSearch.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            Preferences.Set_str(this, "search", etSearch.getText().toString());
            Log.d(TAG, "configView() search " + etSearch.getText());

            startActivity(intent);

        });
    }
}