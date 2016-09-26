package apporio.com.vehicleapp.Parsing_Files;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.DriverdetailsActivity;
import apporio.com.vehicleapp.HomeScreen.MainActivity;
import apporio.com.vehicleapp.HomeScreen.ShipmentActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.CancelRideResult;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 5/19/2016.
 */
public class Parsing_for_CancelRide {

    public static RequestQueue queue;
    public static StringRequest request;
    public static ProgressDialog pDialog;

    public static void CancelRide(final Context changestatus, String Rid,String Rstatus,String pn){

        String StatusURL= all_Api_s.cancelRide.concat(Rid).concat(all_Api_s.cancelRide1).concat(Rstatus).concat(all_Api_s.cancelRide2).concat(pn);
        StatusURL=StatusURL.replace(" ", "%20");
        Log.e("cancel ride ",""+StatusURL);

        pDialog = new ProgressDialog(changestatus);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        queue = VolleySingleton.getInstance(changestatus).getRequestQueue();

        request = new StringRequest(Request.Method.GET, StatusURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pDialog.dismiss();

                Log.e("cancel rides status", "" + response);
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    CancelRideResult rstatusResultCore=new CancelRideResult();

                    rstatusResultCore = gson.fromJson(response, CancelRideResult.class);

                    Log.e(" cancel rides status", "" + rstatusResultCore.result);

                    Log.e(" cancel rides message", "" + rstatusResultCore.msg);
                    if (rstatusResultCore.result.equals("1")){

                        changestatus.startActivity(new Intent(changestatus,MainActivity.class));
                        ShipmentActivity.Shipmentactivity.finish();
                        DriverdetailsActivity.driverdetailsactivity.finish();
                    }

                    else {


                    }

                } catch (Exception e) {
                    pDialog.dismiss();
                    Log.e("Exception", "cancel rides status Exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.e("error rides status", "" + error);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue.add(request);




    }




}
