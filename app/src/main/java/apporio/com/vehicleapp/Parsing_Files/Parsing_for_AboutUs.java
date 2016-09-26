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
import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.NavigationActivities.AboutActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.AboutUs_Result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 12/2/2015.
 */
public class Parsing_for_AboutUs {

    public static RequestQueue queue;
    public static StringRequest request;

    public static String Abouttext;

    public static ProgressDialog pDialog;

    public static void ParseAboutus(final Context aboutus) {

        String abotusURL = all_Api_s.Aboutus;
        abotusURL = abotusURL.replace(" ", "%20");

        pDialog = new ProgressDialog(aboutus);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        queue = VolleySingleton.getInstance(aboutus).getRequestQueue();
        request = new StringRequest(Request.Method.GET, abotusURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("respons about us", "" + response);

                pDialog.dismiss();

                try {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    AboutUs_Result abuotResult=new AboutUs_Result();
                 abuotResult=gson.fromJson(response,AboutUs_Result.class);
                 Log.e("", "" + abuotResult.result);

                    Abouttext= abuotResult.Message.desc;

                    AboutActivity.aboutText.setText(Abouttext);

                } catch (Exception e) {

                    Log.e("Exception", "about us Exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.e("error in about us", "" + error);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue.add(request);


    }
}