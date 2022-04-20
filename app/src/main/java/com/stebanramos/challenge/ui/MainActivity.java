package com.stebanramos.challenge.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.stebanramos.challenge.BuildConfig;
import com.stebanramos.challenge.databinding.ActivityMainBinding;
import com.stebanramos.challenge.utilies.Preferences;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    private EditText etSearch;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Log.d(TAG, " onCreate()");


        configView();
    }

    private void configView(){
        Log.d(TAG, " configView()");

        etSearch = binding.etSearch;
        etSearch.requestFocus();
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    Preferences.Set_str(MainActivity.this, "search", etSearch.getText().toString());
                    Log.d(TAG, "configView() search " + etSearch.getText());

                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}