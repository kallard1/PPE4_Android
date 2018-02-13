package fr.area42.mygavolt.API.Helper;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.area42.mygavolt.Models.Intervention;

/**
 * Created by allardk on 06/02/2018.
 */

public class InterventionHelper {

    public static List<Intervention> sendRequest(RequestQueue requestQueue) {
        String request_url = "http://api.area42.fr/interventions";
        final ArrayList<Intervention> interventionList = new ArrayList<>();

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    Intervention intervention = new Intervention();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String date = jsonObject.getString("date");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

                        intervention.setId(jsonObject.getInt("id"));
                        intervention.setDate(format.parse(date));
                        intervention.setCustomerBusinessName(jsonObject.getJSONObject("addressCustomer").getJSONObject("customer").getString("businessName"));
                        intervention.setCustomerFirstName(jsonObject.getJSONObject("addressCustomer").getJSONObject("customer").getString("firstname"));
                        intervention.setCustomerLastName(jsonObject.getJSONObject("addressCustomer").getJSONObject("customer").getString("lastname"));
                        if (jsonObject.getJSONObject("addressCustomer").getJSONObject("contact").length() != 0) {
                            intervention.setContactFirstName(jsonObject.getJSONObject("addressCustomer").getJSONObject("contact").getString("firstname"));
                            intervention.setContactLastName(jsonObject.getJSONObject("addressCustomer").getJSONObject("contact").getString("lastname"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    interventionList.add(intervention);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley error: ", String.valueOf(error));
            }
        });

        requestQueue.add(jsonArrayRequest);

        return interventionList;
    }
}
