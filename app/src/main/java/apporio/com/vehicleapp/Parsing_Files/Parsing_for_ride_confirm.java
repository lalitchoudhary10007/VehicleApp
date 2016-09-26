package apporio.com.vehicleapp.Parsing_Files;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.DriverdetailsActivity;
import apporio.com.vehicleapp.HomeScreen.Helper;
import apporio.com.vehicleapp.HomeScreen.MainActivity;
import apporio.com.vehicleapp.HomeScreen.PickImageActivity;
import apporio.com.vehicleapp.HomeScreen.ShipmentActivity;
import apporio.com.vehicleapp.HomeScreen.WaitingActivity;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.DriverInfoResult;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 5/16/2016.
 */
public class Parsing_for_ride_confirm {

    public static RequestQueue queue;
    public static StringRequest request;
    public static ProgressDialog pDialog;

   public static void RideNowConfirm(final Context Ridenow, String userid, String Plat, String Plng, String Dlat, String Dlng, String time, String date
   , String helper, String cartype, JSONObject recepient, JSONArray shipment,String placeid,String pn){

   String RideNowURL= all_Api_s.Ridenow.concat(userid).concat(all_Api_s.Ridenow1).concat(Plat).concat(all_Api_s.Ridenow2).concat(Plng)
           .concat(all_Api_s.Ridenow3).concat(Dlat).concat(all_Api_s.Ridenow4).concat(Dlng).concat(all_Api_s.Ridenow5).concat(time)
           .concat(all_Api_s.Ridenow6).concat(date).concat(all_Api_s.Ridenow7).concat(helper).concat(all_Api_s.Ridenow8).concat(cartype)
           .concat(all_Api_s.Ridenow9)+recepient+all_Api_s.Ridenow10+shipment+all_Api_s.Ridenow11.concat(placeid).concat(all_Api_s.Ridenow12).concat(pn);

       RideNowURL=RideNowURL.replace(" ","%20");
       Log.e("ride url", "" + RideNowURL);

       pDialog = new ProgressDialog(Ridenow);
       pDialog.setMessage("Please wait...");
       pDialog.setCancelable(false);


       queue= VolleySingleton.getInstance(Ridenow).getRequestQueue();
       request=new StringRequest(Request.Method.GET, RideNowURL, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               Log.e("respons Ride now confrm", "" + response);
               pDialog.dismiss();
               try {
                   GsonBuilder builder=new GsonBuilder();
                   Gson gson=builder.create();

                   DriverInfoResult driverresult=new DriverInfoResult();
                   driverresult=gson.fromJson(response,DriverInfoResult.class);

                   if (driverresult.getResult()==1){


                      ShipmentActivity.ShowRideConfirmDialog();

                       Helper.ShipImagePath.clear();
                       Helper.ShipImages.clear();
                       Helper.ShipInstrunction.clear();
                       Helper.ShipQunatity.clear();
                       Helper.ShipWeight.clear();

                      Helper.Rname="";
                      Helper.Raddress="";
                      Helper.Rphone="";
                      Helper.Remail="";



                   }
                   else{
                       Toast.makeText(Ridenow, ""+driverresult.getMsg(), Toast.LENGTH_SHORT).show();
                   }


               } catch (Exception e) {
                   Log.e("Exception", "ridenow confrm Exception" + e);
               }

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               pDialog.dismiss();
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





