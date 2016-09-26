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
import apporio.com.vehicleapp.Setter_Getter_Files.Forgot_pass_Result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 11/19/2015.
 */
public class Parsing_for_Forgotpassword {

    public static RequestQueue queue;
    public static StringRequest request;

    public static void forgotpasswordparsing(final Context Forgotactivity,String passemail){

        String forgotpasswordurl= all_Api_s.Forgotpassword.concat(passemail);
        forgotpasswordurl=forgotpasswordurl.replace(" ","%20");
        Log.e("forgot password url", "" + forgotpasswordurl);

        queue= VolleySingleton.getInstance(Forgotactivity).getRequestQueue();
        request=new StringRequest(Request.Method.POST, forgotpasswordurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
          Log.e("responseforgot password", "" + response);
                try {
                    GsonBuilder builder=new GsonBuilder();
                    Gson gson=builder.create();

                   Forgot_pass_Result passwordResultCore=new Forgot_pass_Result();
                    passwordResultCore=gson.fromJson(response,Forgot_pass_Result.class);

                    String passresult=passwordResultCore.result;
                    String passmsg=passwordResultCore.msg;

                    if (passresult.equals("1")){
                        Toast.makeText(Forgotactivity, "" + passmsg, Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(Forgotactivity, "" + passmsg, Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                    Log.e("error", "forgot password error" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

         Log.e("error in forgot pass", "" + error);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }
}
