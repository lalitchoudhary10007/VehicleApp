package apporio.com.vehicleapp.Parsing_Files;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.ShipmentActivity;
import apporio.com.vehicleapp.NavigationActivities.RideEstimateResultCostActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.RideEstimateResponse;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 12/8/2015.
 */
public class Parsing_For_RideEstimate {

    public static RequestQueue queue;
    public static StringRequest request;

    public static String totalkm,totalpay;
    public static ProgressDialog pDialog;

    public static void parseRideEstimate(final Context Rideestimate,String Plat,String Plng,String Dlat,String Dlng,
                                         String CTid){
  String RideEstimateURL= all_Api_s.RideEstimate.concat(Plat).concat(all_Api_s.RideEstimate1).concat(Plng).concat(all_Api_s.RideEstimate2)
        .concat(Dlat).concat(all_Api_s.RideEstimate3).concat(Dlng).concat(all_Api_s.RideEstimate4).concat(CTid);

        RideEstimateURL=RideEstimateURL.replace(" ", "%20");
   Log.e("ride esimate urlll", "" + RideEstimateURL);

        pDialog = new ProgressDialog(Rideestimate);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        queue= VolleySingleton.getInstance(Rideestimate).getRequestQueue();

        request=new StringRequest(Request.Method.GET, RideEstimateURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "ride estimate response" + response);
              pDialog.dismiss();
                try {
                    GsonBuilder builder=new GsonBuilder();
                    Gson gson=builder.create();

                    RideEstimateResponse RideEresponse=new RideEstimateResponse();
                    RideEresponse=gson.fromJson(response,RideEstimateResponse.class);


                    if (RideEresponse.getResponse().getResult()==1){

                        Log.e("total km",""+RideEresponse.getResponse().getMessage().getTotal_km());
                        Log.e("total pay",""+RideEresponse.getResponse().getMessage().getTot_pay());
                        RideEstimateResultCostActivity.Tdistane.setText(RideEresponse.getResponse().getMessage().getTotal_km());
                        RideEstimateResultCostActivity.Tpay.setText(String.valueOf(RideEresponse.getResponse().getMessage().getTot_pay()));
//                        Log.e("Approax total distance",""+RideEresponse.response.Message.total_km);
//                        String[] parts=RideEresponse.response.Message.tot_pay.split("to");
//                        String one=parts[0];
//                        String two=parts[1];
//
//                        RideEstimateResultCostActivity.pay1.setText("INR " + one);
//                        RideEstimateResultCostActivity.pay2.setText("INR" + two);
//                        RideEstimateResultCostActivity.Tdistane.setText(RideEresponse.response.Message.total_km);
//
//
//
//
//                        ShipmentActivity.paymentLinearLayout.setVisibility(View.VISIBLE);
//                        ShipmentActivity.total_KM.setText(RideEresponse.response.Message.total_km);
//                        ShipmentActivity.total_Price.setText("INR" + two);

                    }

                } catch (Exception e) {
                    Log.e("exception", "ride estimate exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "ride estimate error" + error);
                pDialog.dismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue.add(request);

    }
}
