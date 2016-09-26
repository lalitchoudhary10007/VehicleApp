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
import com.squareup.picasso.Picasso;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.MainActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.RateCardResponse;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 5/16/2016.
 */
public class Parsing_Rate_Card {

    public static RequestQueue queue;
    public static StringRequest request;

  public static void ParseRateCard(final Context RateCardActivty,String cartype){

      String RateURL= all_Api_s.RateCard.concat(cartype);
      RateURL=RateURL.replace(" ","%20");
      Log.e("rate card url", "" + RateURL);

      queue= VolleySingleton.getInstance(RateCardActivty).getRequestQueue();
      request=new StringRequest(Request.Method.GET, RateURL, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
              Log.e("rate card response",""+response);
              try {
                  GsonBuilder builder = new GsonBuilder();
                  Gson gson = builder.create();

                  RateCardResponse rateCardResponse=new RateCardResponse();
                  rateCardResponse=gson.fromJson(response,RateCardResponse.class);
                  String rateCArdresult=rateCardResponse.rateCardResult.result;


                  if (rateCArdresult.equals("1")){

                      MainActivity.carname.setText(rateCardResponse.rateCardResult.rateCardDetails.car_type_name);
                      MainActivity.carcapacity.setText(rateCardResponse.rateCardResult.rateCardDetails.car_capacity);
                      MainActivity.minfare.setText("$ "+rateCardResponse.rateCardResult.rateCardDetails.minimum_rate);
                      MainActivity.pickfare.setText("$ "+rateCardResponse.rateCardResult.rateCardDetails.pick_up_rate);
                      MainActivity.milfare.setText("$ "+rateCardResponse.rateCardResult.rateCardDetails.per_mile_rate);
                      MainActivity.minutefare.setText("$ " + rateCardResponse.rateCardResult.rateCardDetails.per_minute_rate);


                      Picasso.with(RateCardActivty).load("http://apporio.in/load_up_app/" + rateCardResponse.rateCardResult.rateCardDetails.car_type_image).fit().into(MainActivity.carimageOndialog);

                  }else {
                      Log.e("there is no data","dattttt");

                  }
              }catch (Exception e){
                  Log.e("error", "rate card exception" + e);
              }
          }

      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              Log.e("error", "rate card error" + error);
          }
      });
      request.setRetryPolicy(new DefaultRetryPolicy(50000,
              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      queue.add(request);


  }

}
