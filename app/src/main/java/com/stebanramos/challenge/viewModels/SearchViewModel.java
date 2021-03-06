package com.stebanramos.challenge.viewModels;

import static com.stebanramos.challenge.utilies.Urls.SEARCH_PRODCUTS_URL;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.stebanramos.challenge.models.Product;
import com.stebanramos.challenge.utilies.Utils;
import com.stebanramos.challenge.utilies.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";

    private String id, title, price, available_quantity, condition, permalink, thumbnail, category_id;
    private boolean free_shipping;

    private RequestQueue mQueue;
    private List<Product> productList;
    public MutableLiveData<String> searchInput = new MutableLiveData<>();
    private MutableLiveData<List<Product>> muProductList;

    public LiveData<List<Product>> getData() {
        Log.d(TAG, "getData()");

        muProductList = new MutableLiveData<>();

        return muProductList;
    }

    //update the value of the search field
    public void setSearchInput(String result, Context context) {
        Log.d(TAG, "setSearchInput()");

        searchInput.setValue(result);
        loadData(context);
    }

    // Do an asynchronous operation to fetch products.
    private void loadData(final Context context) {
        Log.d(TAG, "loadData()");

        try {
            productList = new ArrayList<>();
            Uri baseUri = Uri.parse(SEARCH_PRODCUTS_URL);
            Uri.Builder builder = baseUri.buildUpon();

            builder.appendQueryParameter("q", searchInput.getValue());

            Log.d(TAG, "loadData() uri " + builder);

            VolleySingleton.getInstance(context).addToRequestQueue(

                    new JsonObjectRequest(Request.Method.GET, builder.toString(), null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //progressBar.setVisibility(View.GONE);
                                        //get the array called results
                                        JSONArray results = response.getJSONArray("results");
                                        Log.d(TAG, "loadData() onResponse " + results);

                                        //iterate in every object inside the array
                                        for (int i = 0; i < results.length(); i++) {
                                            JSONObject products = results.getJSONObject(i);

                                            Log.d(TAG, "loadData() onResponse " + results.getJSONObject(i));

                                            id = products.getString("id");
                                            title = products.getString("title");
                                            price = products.getString("price");
                                            available_quantity = products.getString("available_quantity");
                                            condition = products.getString("condition");
                                            permalink = products.getString("permalink");
                                            thumbnail = products.getString("thumbnail");
                                            category_id = products.getString("category_id");
                                            free_shipping = products.getJSONObject("shipping").getBoolean("free_shipping");

                                            productList.add(new Product(id, title, price, available_quantity, condition, permalink, thumbnail, category_id, free_shipping));
                                        }
                                        muProductList.setValue(productList);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //just print the error and notify the user for some technical problems
                                    error.printStackTrace();
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            })

            );
        } catch (Exception e) {
            Utils.printtCatch(e, "loadData", TAG);
        }

    }
}
