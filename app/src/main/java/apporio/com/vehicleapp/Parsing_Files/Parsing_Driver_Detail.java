package apporio.com.vehicleapp.Parsing_Files;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.DriverdetailsActivity;
import apporio.com.vehicleapp.HomeScreen.Helper;
import apporio.com.vehicleapp.HomeScreen.WaitingActivity;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.DriverInfoResult;
import apporio.com.vehicleapp.Setter_Getter_Files.View_Driver_Result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by Lenovo on 9/10/2016.
 */
public class Parsing_Driver_Detail {


    public static RequestQueue queue;
    public static StringRequest request;


   public static double driverlat,driverlang;


    public static void DriverDetail(final Context Ridenow, String rideid  ){


        String RideNowURL= all_Api_s.View_Driver.concat(rideid);


        RideNowURL=RideNowURL.replace(" ","%20");
        Log.e("ride url", "" + RideNowURL);

        final ProgressDialog  pDialog = new ProgressDialog(Ridenow);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        queue= VolleySingleton.getInstance(Ridenow).getRequestQueue();
        request=new StringRequest(Request.Method.GET, RideNowURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("respons Ride now confrm", "" + response);
                pDialog.dismiss();
                pDialog.cancel();
                try {
                    GsonBuilder builder=new GsonBuilder();
                    Gson gson=builder.create();

                    View_Driver_Result driverinfo=new View_Driver_Result();
                    driverinfo=gson.fromJson(response,View_Driver_Result.class);

                    if (driverinfo.getResult()==1){

                        DriverdetailsActivity.getthetracker(driverlat , driverlang );

                        DriverdetailsActivity. time=  driverinfo .getMessage().getDuration();
                        DriverdetailsActivity. driverid=driverinfo .getMessage().getDriver_id();
                        driverlat= Double.valueOf(driverinfo .getMessage().getCurrent_lat());
                        driverlang=Double.valueOf(driverinfo.getMessage().getCurrent_long());
                        DriverdetailsActivity. drivernme.setText(driverinfo .getMessage().getDriver_name());
                        DriverdetailsActivity . drivercarno.setText(driverinfo .getMessage().getCar_number());
                        DriverdetailsActivity . drivercarname.setText(driverinfo .getMessage().getCar_type_name());
                        DriverdetailsActivity. im=driverinfo .getMessage().getDriver_image();
                        DriverdetailsActivity. star= String.valueOf(driverinfo.getMessage().getDriver_rating());
                        DriverdetailsActivity . ratingBarGreen.setScore(Float.parseFloat(DriverdetailsActivity.star));
                        Picasso.with(Ridenow).load("http://apporio.in/load_up_app/"+DriverdetailsActivity. im)
                          .error(R.drawable.defaultprof)
                          .placeholder(R.drawable.defaultprof)
                           .into(DriverdetailsActivity.driverimage);

                      //  WaitingActivity.waitingActivity.finish();


                    }
                    else{
                        pDialog.dismiss();
                        pDialog.cancel();
                        Toast.makeText(Ridenow, "Required Field Missing !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    pDialog.dismiss();
                    Log.e("Exception", "ridenow confrm Exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                pDialog.cancel();
                Log.e("error in ridenow confrm",""+error);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue.add(request);

    }







}
