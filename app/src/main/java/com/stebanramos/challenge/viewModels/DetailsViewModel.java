package com.stebanramos.challenge.viewModels;

import static com.stebanramos.challenge.utilies.Urls.SEARCH_ITEM_URL;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.stebanramos.challenge.models.Item;
import com.stebanramos.challenge.utilies.Preferences;
import com.stebanramos.challenge.utilies.Utils;
import com.stebanramos.challenge.utilies.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";

    private MutableLiveData<Item> muItem;
    private MutableLiveData<String> description;

    private String id, title, price;
    private JSONArray pictures, attributes;
    private Item item;

    public LiveData<Item> getData(Context context) {
        Log.d(TAG, "getData()");

        if (muItem == null) {
            muItem = new MutableLiveData<>();
            loadData(context);
        }
        return muItem;
    }

    public LiveData<String> getDescription(Context context) {
        Log.d(TAG, "getDescription()");

        if (description == null) {
            description = new MutableLiveData<>();
            loadDescription(context);
        }
        return description;
    }

    // Do an asynchronous operation to fetch items.
    private void loadData(final Context context) {
        Log.d(TAG, "loadData()");
        try {
            VolleySingleton.getInstance(context).addToRequestQueue(

                    new JsonObjectRequest(Request.Method.GET, SEARCH_ITEM_URL + Preferences.Get_str(context, "itemId"), null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Log.d(TAG, "loadData() onResponse " + response);

                                        id = response.getString("id");
                                        title = response.getString("title");
                                        pictures = response.getJSONArray("pictures");
                                        price = response.getString("price");
                                        attributes = response.getJSONArray("attributes");

                                        item = new Item(id, title, pictures, price, attributes);
                                        muItem.setValue(item);

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

    // Do an asynchronous operation to fetch item description.

    private void loadDescription(final Context context) {
        Log.d(TAG, "loadDescription()");

        try {
            VolleySingleton.getInstance(context).addToRequestQueue(

                    new JsonObjectRequest(Request.Method.GET, SEARCH_ITEM_URL + Preferences.Get_str(context, "itemId") + "/description", null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Log.d(TAG, "loadDescription() onResponse " + response);
                                        String results = response.get("plain_text").toString();
                                        Log.d(TAG, "loadDescription() onResponse " + results);
                                        description.setValue(results);

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
