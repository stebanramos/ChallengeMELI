package com.stebanramos.challenge.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.stebanramos.challenge.databinding.ActivityDetailsBinding;
import com.stebanramos.challenge.models.Item;
import com.stebanramos.challenge.models.Product;
import com.stebanramos.challenge.viewModels.DetailsViewModel;
import com.stebanramos.challenge.viewModels.SearchViewModel;

import org.json.JSONException;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";


    private ActivityDetailsBinding binding;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Log.d(TAG, "onCreate()");

        DetailsViewModel detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        detailsViewModel.getData(this).observe(this, new Observer<Item>() {
            @Override
            public void onChanged(@Nullable Item liveItem) {
                item = liveItem;
                configView();
            }
        });

    }

    private void configView() {

        try {
            binding.tvTittle.setText(item.getTitle());
            Glide.with(this)
                    .asBitmap()
                    .load(item.getPíctures().getJSONObject(0).get("secure_url"))
                    .into(binding.ivItem);
            binding.tvPrice.setText(item.getPrice());
            binding.tvAttributes.setText(item.getAttributes().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}