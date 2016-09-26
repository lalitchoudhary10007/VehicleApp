package apporio.com.vehicleapp.Parsing_Files;

import android.content.Context;
import android.util.Log;
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
import apporio.com.vehicleapp.Setter_Getter_Files.call_support_result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 5/11/2016.
 */
public class Parsing_for_callSupport {


    public static RequestQueue queue;
    public static StringRequest request;
    public static String callno;

    public static void parseCallSupport(final Context callSupport){

        String callsupportURL= all_Api_s.callSupport;
        callsupportURL=callsupportURL.replace(" ","%20");

        queue = VolleySingleton.getInstance(callSupport).getRequestQueue();


        request = new StringRequest(Request.Method.GET, callsupportURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("call support", "" + response);
                try {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    call_support_result resp=new call_support_result();
                    resp=gson.fromJson(response,call_support_result.class);
                    Log.e("",""+resp.result);

                  if (resp.result.equals("1")){
                      callno=resp.Message.phone_number;
                  }else {
                     // Toast.makeText(callSupport, "not valid no.", Toast.LENGTH_SHORT).show();
                  }



                } catch (Exception e) {

                    Log.e("Exception", "call suport" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error in calll support", "" + error);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }



}
