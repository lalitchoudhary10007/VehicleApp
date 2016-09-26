package apporio.com.vehicleapp.Parsing_Files;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apporio.com.vehicleapp.HomeScreen.Helper;
import apporio.com.vehicleapp.HomeScreen.MainActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.ridedestsettergetter;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 12/8/2015.
 */
public class Parsing_For_LatLong {

    public static RequestQueue queue2222;
    public static StringRequest sr1, sr2222;

    public static Double lat;
    public static Double lng;
   public static String selectedtext;

    public static ProgressDialog pDialog;

    public static void parsing(final Context activity, String s) {


        String locationurl2 = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + s + "&key=AIzaSyCC3Ci--XByh-o-ukFw0IBOGD1of7hglA4";
        // Toast.makeText(activity, ""+s, Toast.LENGTH_SHORT).show();
        locationurl2 = locationurl2.replace(" ", "%20");
         Log.e("url", "" + locationurl2);

        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        queue2222 = VolleySingleton.getInstance(activity).getRequestQueue();

        sr2222 = new StringRequest(Request.Method.GET, locationurl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                Log.e("", "" + response);
                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    final Gson gson = gsonBuilder.create();

                    ridedestsettergetter received2 = new ridedestsettergetter();
                    received2 = gson.fromJson(response, ridedestsettergetter.class);


                    lat = Double.parseDouble(received2.innerdestination.geometry.location11.lat);
                    lng = Double.parseDouble(received2.innerdestination.geometry.location11.lng);



//                    Directionactivity.point11=new LatLng(lat,lng);
//                    Directionactivity.i=0;
//                    Directionactivity.arrive.setVisibility(View.GONE);
//                    Directionactivity.endtrip.setVisibility(View.VISIBLE);
                    //  List<HashMap<String, String>> result =descp;

                    if (Helper.checkPay.equals("1")){
                        Helper.Address_Lat= String.valueOf(lat);
                        Helper.Address_Lng=String.valueOf(lng);
                    }else {
                        Helper.DestinationAddress_Lat= String.valueOf(lat);
                        Helper.DestinationAddress_Lng=String.valueOf(lng);

                        if (Helper.Address_Lat.equals("")&&Helper.Address_Lng.equals("")){
                            Parsing_For_RideEstimate.parseRideEstimate(activity, MainActivity.usercurrentlat,MainActivity.usercurrentlong,Helper.DestinationAddress_Lat,Helper.DestinationAddress_Lng,
                                    MainActivity.carid);
                        }else {
                            Parsing_For_RideEstimate.parseRideEstimate(activity, Helper.Address_Lat,Helper.Address_Lng,Helper.DestinationAddress_Lat,Helper.DestinationAddress_Lng,
                                    MainActivity.carid);
                        }


                    }



                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("exception", "" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("exception", "" + error);
                pDialog.dismiss();
            }
        });
        sr2222.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue2222.add(sr2222);


    }
}