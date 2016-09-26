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
import apporio.com.vehicleapp.Setter_Getter_Files.Rating_result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 1/13/2016.
 */
public class Parsing_for_rating {

    public static RequestQueue queue;
    public static StringRequest request;

    public static void Parse_Rating(final Context RatingActivity,String usid,String Drid,String star){
     String RatingURL= all_Api_s.Rating.concat(usid).concat(all_Api_s.Rating1).concat(Drid).concat(all_Api_s.Rating2).concat(star);

      RatingURL=RatingURL.replace(" ", "%20");
        Log.e("", "" + RatingURL);

        queue= VolleySingleton.getInstance(RatingActivity).getRequestQueue();
        request=new StringRequest(Request.Method.POST, RatingURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("response", "rating" + response);
try {


                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();

    Rating_result ratingResult=new Rating_result();
    ratingResult=gson.fromJson(response,Rating_result.class);

                if(ratingResult.result.equals("1")){
                    Toast.makeText(RatingActivity, "" + ratingResult.msg, Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(RatingActivity, "" + ratingResult.msg, Toast.LENGTH_SHORT).show();
                }
}catch (Exception e){
    Log.e("Exception", "rating" + e);
}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response", "rating error" + error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

}
