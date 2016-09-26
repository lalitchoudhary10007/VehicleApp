package apporio.com.vehicleapp.Parsing_Files;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apporio.com.vehicleapp.Adapters.ShipMentAdapter;
import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.ShipmentActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.Delete_Shipment_Result;
import apporio.com.vehicleapp.Setter_Getter_Files.View_Shipment_Only_Result;
import apporio.com.vehicleapp.Setter_Getter_Files.View_Shipment_Result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by apporio5 on 13-07-2016.
 */
public class Parsing_For_Delete_Shipment {

    public static RequestQueue queue;
    public static StringRequest request;


    public static ProgressDialog pDialog;

    public static void Delete_Shipment(final Context home,String ship_id){

        String deleteshipURL= all_Api_s.Delete_Shipment.concat(ship_id);

        deleteshipURL=deleteshipURL.replace(" ", "%20");
        Log.e("shipment url", "" + deleteshipURL);

        pDialog = new ProgressDialog(home);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        queue = VolleySingleton.getInstance(home).getRequestQueue();

        request = new StringRequest(Request.Method.GET, deleteshipURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("respons home screen", "" + response);

                pDialog.dismiss();

                try {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    Delete_Shipment_Result response1=new Delete_Shipment_Result();
                    response1=gson.fromJson(response,Delete_Shipment_Result.class);

                    if (response1.result.toString().equals("1")){

                       // Parsing_for_view_shipment.ViewShipment(home);

                    }else {


                    }



                } catch (Exception e) {
                    pDialog.dismiss();
                    Log.e("Exception", "home screen Exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error in home screen", "" + error);
                pDialog.dismiss();

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue.add(request);

    }


}
