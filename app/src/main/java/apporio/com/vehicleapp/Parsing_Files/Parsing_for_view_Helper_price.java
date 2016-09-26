package apporio.com.vehicleapp.Parsing_Files;

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

import java.util.ArrayList;
import java.util.List;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.Setter_Getter_Files.Helper_Price_Result;
import apporio.com.vehicleapp.Setter_Getter_Files.View_Weight_Result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 6/15/2016.
 */
public class Parsing_for_view_Helper_price {

    public static RequestQueue queue;
    public static StringRequest request;

    public static List<Helper_Price_Result.Helper_Price_Details> priceList;
    public static ArrayList<String> Price_list = new ArrayList<String>();


    public static void View_Price(final Context price){

        String viewCarURL= all_Api_s.View_Helper_Price;
        queue= VolleySingleton.getInstance(price).getRequestQueue();

        request=new StringRequest(Request.Method.GET, viewCarURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "view car response" + response);

                try {
                    GsonBuilder builder=new GsonBuilder();
                    Gson gson=builder.create();



                    Helper_Price_Result helperResult=new Helper_Price_Result();
                    helperResult=gson.fromJson(response,Helper_Price_Result.class);

                    if (helperResult.result.equals("1")){

                        priceList=helperResult.Message;


                        for (int i = 0; i <priceList.size(); i++) {

                            Price_list.add(priceList.get(i).Helper_price);
                          }

                    }else {

                    }

                } catch (Exception e) {
                    Log.e("error", "view car error" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "view car error" + error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }

}
