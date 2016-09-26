package apporio.com.vehicleapp.GooglePlaces;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import apporio.com.vehicleapp.HomeScreen.PickImageActivity;
import apporio.com.vehicleapp.NavigationActivities.RideEstimateResultCostActivity;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by saifi45 on 9/17/2015.
 */
public class parsingforoffers {
    public static String[] from = new String[] { "description"};
    public static int[] to = new int[] { android.R.id.text1 };
    public static RequestQueue queue;
    public static StringRequest sr1,sr2;
    public static List<Innerplaces> data_list1;
    public static ArrayList<String> descp = new ArrayList<String>();
    public static ArrayList<String> tagline = new ArrayList<String>();
    public static ArrayList<String> imagesss = new ArrayList<String>();
    public static ArrayList<String> postid = new ArrayList<String>();

    public static void parsing(final Context activity, String s){



        String locationurl2 = "https://maps.googleapis.com/maps/api/place/autocomplete/json?&input="+s+"&key=AIzaSyCC3Ci--XByh-o-ukFw0IBOGD1of7hglA4";

        locationurl2= locationurl2.replace(" ","%20");
       // Log.e("url", "" + locationurl2);
        queue = VolleySingleton.getInstance(activity).getRequestQueue();
        sr2 = new StringRequest(Request.Method.GET, locationurl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                descp.clear();
                postid.clear();


                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    final Gson gson = gsonBuilder.create();
                    placessettergetter received2 = new placessettergetter();
                    received2 = gson.fromJson(response, placessettergetter.class);


                    data_list1=received2.predictionsplace;
                    for(int i=0;i<data_list1.size();i++){
                        descp.add(data_list1.get(i).description);
                         postid.add(data_list1.get(i).place_id);

                    }

                  //  List<HashMap<String, String>> result =descp;


                    // Setting the adapter

//                    if (PickImageActivity.setAdapterLogic.equals("1")){
//
//                    }else if (PickImageActivity.setAdapterLogic.equals("0")){
//                      RideEstimateResultCostActivity.atvPlaces.setAdapter(adapter);
//                    }
                  //  PickImageActivity.atvPlaces2.setAdapter(adapter);


                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Log.e("exception", "" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        sr2.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr2);

    }
}
