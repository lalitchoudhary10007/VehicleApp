package apporio.com.vehicleapp.Parsing_Files;

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

import java.util.ArrayList;
import java.util.List;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.PickImageActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.View_Cars_Result;
import apporio.com.vehicleapp.Setter_Getter_Files.View_Weight_Result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 6/15/2016.
 */
public class Parsing_View_Weight {

    public static List<View_Weight_Result.weightDetails> weightList;
    public static ArrayList<String> weight_Range = new ArrayList<String>();
    public static ArrayList<String> weight_Status = new ArrayList<String>();

    public static RequestQueue queue;
    public static StringRequest request;

  public static void View_weight(final Context viewWeight){

      String viewCarURL= all_Api_s.View_Weight;
      queue= VolleySingleton.getInstance(viewWeight).getRequestQueue();

      request=new StringRequest(Request.Method.GET, viewCarURL, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
              Log.e("response", "view car response" + response);

              try {
                  GsonBuilder builder=new GsonBuilder();
                  Gson gson=builder.create();

                  weight_Range.clear();
                  weight_Status.clear();


                  View_Weight_Result weightResult=new View_Weight_Result();
                  weightResult=gson.fromJson(response,View_Weight_Result.class);

                   if (weightResult.result.equals("1")){

                       weightList=weightResult.Message;


                       for (int i = 0; i <weightList.size(); i++) {

                          weight_Range.add(weightList.get(i).weight_range);
                           weight_Status.add(weightList.get(i).status);

                       }

                       ArrayAdapter ad= new ArrayAdapter(viewWeight, android.R.layout.simple_spinner_item,weight_Range);
                       ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       PickImageActivity.WeightSpinner.setAdapter(ad);


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
