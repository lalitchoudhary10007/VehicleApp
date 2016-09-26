package apporio.com.vehicleapp.Parsing_Files;

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

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.NavigationActivities.AboutActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.Tand_result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 11/19/2015.
 */
public class Parsing_for_termsandconditions {

    public static RequestQueue queue;
    public static StringRequest request;
    public static String data;

    public static void termsand_cnditions_Parse(final Context termscondinsactivity){
        String termscoditionsurl= all_Api_s.TermsAndConditions;
        termscoditionsurl=termscoditionsurl.replace(" ","%20");

        queue= VolleySingleton.getInstance(termscondinsactivity).getRequestQueue();

        request=new StringRequest(Request.Method.GET, termscoditionsurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "terms&conditions response" + response);


               try {
                    GsonBuilder builder=new GsonBuilder();
                    Gson gson=builder.create();

                    Tand_result outerCore=new Tand_result();
                    outerCore=gson.fromJson(response,Tand_result.class);

                   data=outerCore.innerCore.t_and_c;
                 //  AboutActivity.tnc2.setText(data);

                } catch (Exception e) {
                    Log.e("error", "terms&conditions error" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "terms&conditions error" + error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }
}
