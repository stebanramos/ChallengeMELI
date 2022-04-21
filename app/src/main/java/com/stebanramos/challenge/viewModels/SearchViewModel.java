package com.stebanramos.challenge.viewModels;

import static com.stebanramos.challenge.utilies.Urls.SEARCH_ITEM_URL;
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
import com.stebanramos.challenge.models.Item;
import com.stebanramos.challenge.models.Product;
import com.stebanramos.challenge.utilies.GsonRequest;
import com.stebanramos.challenge.utilies.Preferences;
import com.stebanramos.challenge.utilies.Utils;
import com.stebanramos.challenge.utilies.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";

    private String id, title, price, available_quantity, condition, permalink, thumbnail, attributes, category_id;
    private JSONObject installments;
    private boolean free_shipping;

    private RequestQueue mQueue;
    private List<Product> productList = new ArrayList<>();
    public MutableLiveData<String> searchInput = new MutableLiveData<>();
    private MutableLiveData<List<Product>> muProductList;

    public LiveData<List<Product>> getData(Context context) {
        Log.d(TAG, "getData()");

        if (muProductList == null) {
            muProductList = new MutableLiveData<>();
            loadData(context);
        }
        return muProductList;
    }

    public void setSearchInput(String result) {
        Log.d(TAG, "setSearchInput()");

        searchInput.setValue(result);
    }

    private void loadData(final Context context) {
        Log.d(TAG, "loadData()");

        try {
            VolleySingleton.getInstance(context).addToRequestQueue(

                    new GsonRequest<JSONObject>(SEARCH_ITEM_URL + "ids"+ Preferences.Get_str(context, "itemId"),
                            null,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
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
                                            attributes = products.getString("attributes");
                                            category_id = products.getString("category_id");
                                            installments = products.getJSONObject("installments");
                                            free_shipping = products.getJSONObject("shipping").getBoolean("free_shipping");

                                            productList.add(new Product(id, title, price, available_quantity, condition, permalink, thumbnail, attributes, category_id, installments, free_shipping));
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
                                    Log.d(TAG, "Error Volley:" + error.getMessage());
                                    error.printStackTrace();
                                }
                            }
                    )
            );
        } catch (Exception e) {
            Utils.printtCatch(e, "loadData", TAG);
        }

    }
}
