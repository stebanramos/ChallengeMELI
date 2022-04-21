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
import com.stebanramos.challenge.utilies.Utils;
import com.stebanramos.challenge.viewModels.DetailsViewModel;
import com.stebanramos.challenge.viewModels.SearchViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same DetailsViewModel instance created by the first activity.

        try {
            DetailsViewModel detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
            detailsViewModel.getData(this).observe(this, new Observer<Item>() {
                @Override
                public void onChanged(@Nullable Item liveItem) {
                    // update UI
                    item = liveItem;
                    configView();
                }
            });

            detailsViewModel.getDescription(this).observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String liveDesc) {
                    // update UI item description
                    binding.tvDescription.setText(liveDesc);
                }
            });
        }catch(Exception e){
            Utils.printtCatch(e, "onCreate", TAG);
        }


    }

    private void configView() {
        Log.i(TAG, "configView");

        try {
            binding.tvTittle.setText(item.getTitle());
            Glide.with(this)
                    .asBitmap()
                    .load(item.getPíctures().getJSONObject(0).get("secure_url"))
                    .into(binding.ivItem);

            binding.tvPrice.setText(item.getPrice());

            JSONArray attributes = item.getAttributes();

            String caracteristicas = "";
            String others = "";
            for (int i = 0; i < attributes.length(); i++) {

                JSONObject json = attributes.getJSONObject(i);
                Log.d(TAG, "configView() id " + json.get("value_id"));

                if (!json.isNull("value_id")){
                    others += json.get("name") + ": " + json.get("value_name") + "\n";
                }else{

                    caracteristicas += json.get("name") + ": " + json.get("value_name") + "\n";
                }

            }

            binding.tvAttributes.setText(caracteristicas);
            binding.tvOtherAttributes.setText(others);


        } catch (JSONException e) {
            Utils.printtCatch(e, "configView", TAG);        }
    }
}