package com.stebanramos.challenge.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.stebanramos.challenge.adapters.ProductsAdapter;
import com.stebanramos.challenge.databinding.ActivitySearchBinding;
import com.stebanramos.challenge.models.Product;
import com.stebanramos.challenge.utilies.Preferences;
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


        initRecyclerView();
        SearchViewModel searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.setSearchInput(Preferences.Get_str(this, "search"));
        searchViewModel.getData(this).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> productsList) {
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
    }


    private void initRecyclerView() {

        Log.d(TAG, " initRecyclerView()");

        binding.searchRecyclerView.setHasFixedSize(true);
        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpRecyclerView(final List<Product> products){

        Log.d(TAG, " setUpRecyclerView()");

        //then we set up the adapter with our filled list and set it to the recycler view
        ProductsAdapter adapter = new ProductsAdapter(SearchActivity.this, (ArrayList<Product>) products);
        binding.searchRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position,Product product, ImageView imageView) {

                Product currentProduct = products.get(position);

                Preferences.Set_str(getApplicationContext(), "itemId", currentProduct.getId());

                //go Web View MELI
                /*Product currentProduct = products.get(position);
                Intent intent = new Intent(SearchActivity.this, DetailsWebView.class);
                intent.putExtra("permalink", currentProduct.getPermalink());*/

                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                intent.putExtra("transition_name", ViewCompat.getTransitionName(imageView));

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        SearchActivity.this, imageView, ViewCompat.getTransitionName(imageView)
                );

                startActivity(intent, optionsCompat.toBundle());
            }
        });

    }
}