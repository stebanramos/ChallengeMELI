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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.stebanramos.challenge.models.Item;
import com.stebanramos.challenge.utilies.Preferences;
import com.stebanramos.challenge.utilies.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DetailsViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";

    private RequestQueue mQueue;
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

    private void loadData(final Context context) {
        mQueue = VolleySingleton.getInstance(context).getRequestQueue();
        Uri baseUri = Uri.parse(SEARCH_ITEM_URL);
        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("ids", Preferences.Get_str(context, "itemId"));

        Log.d(TAG, "loadData() uri " + builder);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, builder.toString(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d(TAG, "loadData() onResponse " + response);
                            JSONObject results = response.getJSONObject(0).getJSONObject("body");
                            Log.d(TAG, "loadData() onResponse " + results);

                            id = results.getString("id");
                            title = results.getString("title");
                            pictures = results.getJSONArray("pictures");
                            price = results.getString("price");
                            attributes = results.getJSONArray("attributes");

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
                });

        mQueue.add(request);
    }

    private void loadDescription(final Context context) {
        mQueue = VolleySingleton.getInstance(context).getRequestQueue();
        Uri baseUri = Uri.parse(SEARCH_ITEM_URL + Preferences.Get_str(context, "itemId")+"/description");
        Uri.Builder builder = baseUri.buildUpon();

        //builder.appendQueryParameter("", "MCO599728938/description");

        Log.d(TAG, "loadDescription() uri " + builder);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, builder.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "loadData() onResponse " + response);
                            String results = response.get("plain_text").toString();
                            Log.d(TAG, "loadData() onResponse " + results);
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
                });

        mQueue.add(request);
    }
}
