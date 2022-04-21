package com.stebanramos.challenge.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stebanramos.challenge.R;
import com.stebanramos.challenge.adapters.ProductsAdapter;
import com.stebanramos.challenge.databinding.ActivitySearchBinding;
import com.stebanramos.challenge.models.Product;
import com.stebanramos.challenge.utilies.Preferences;
import com.stebanramos.challenge.utilies.Utils;
import com.stebanramos.challenge.viewModels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Log.d(TAG, " onCreate()");

        initComponents();

    }

    private void initComponents() {
        Log.d(TAG, " initComponents()");

        try {
            if (Utils.Network_Connected(this)) {
                initRecyclerView();
                // Create a ViewModel the first time the system calls an activity's onCreate() method.
                // Re-created activities receive the same DetailsViewModel instance created by the first activity.
                SearchViewModel searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
                searchViewModel.setSearchInput(Preferences.Get_str(this, "search"), this);
                searchViewModel.getData().observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(@Nullable List<Product> productsList) {
                        // update UI

                        if (productsList != null && productsList.size() > 0) {
                            binding.searchProgress.setVisibility(View.GONE);

                        }
                        setUpRecyclerView(productsList);
                    }
                });
                searchViewModel.searchInput.observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.d(TAG, "onChanged: the new search value is : " + s);
                    }
                });

                //search keyboard action
                binding.etSearch.clearFocus();
                binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            searchViewModel.setSearchInput(binding.etSearch.getText().toString(), SearchActivity.this);

                        }
                        return false;
                    }
                });

            } else {
                showSplashFailConnection();
            }
        } catch (Exception e) {
            Utils.printtCatch(e, "initComponents", TAG);
        }

    }

    private void initRecyclerView() {

        Log.d(TAG, " initRecyclerView()");

        try {
            binding.searchRecyclerView.setHasFixedSize(true);
            binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } catch (Exception e) {
            Utils.printtCatch(e, "initRecyclerView", TAG);

        }

    }

    private void setUpRecyclerView(final List<Product> products) {

        Log.d(TAG, " setUpRecyclerView()");

        try {
            //then we set up the adapter with our filled list and set it to the recycler view
            ProductsAdapter adapter = new ProductsAdapter(SearchActivity.this, (ArrayList<Product>) products);
            binding.searchRecyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, Product product, ImageView imageView) {

                    Product currentProduct = products.get(position);

                    Preferences.Set_str(getApplicationContext(), "itemId", currentProduct.getId());

                    Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                    intent.putExtra("transition_name", ViewCompat.getTransitionName(imageView));

                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            SearchActivity.this, imageView, ViewCompat.getTransitionName(imageView)
                    );

                    startActivity(intent, optionsCompat.toBundle());
                }
            });
        } catch (Exception e) {
            Utils.printtCatch(e, "setUpRecyclerView", TAG);

        }


    }

    private void showSplashFailConnection() {
        Log.d(TAG, " showSplashFailConnection()");

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_fail_connection, null);
            builder.setView(view);

            Button btnTryAgain = view.findViewById(R.id.btnTryAgain);

            AlertDialog alertDialog = builder.create();

            btnTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    initComponents();
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        }catch (Exception e){
            Utils.printtCatch(e, "showSplashFailConnection", TAG);

        }


    }
}