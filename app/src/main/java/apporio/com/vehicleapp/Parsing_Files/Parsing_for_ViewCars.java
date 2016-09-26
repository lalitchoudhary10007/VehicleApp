package apporio.com.vehicleapp.Parsing_Files;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
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
import apporio.com.vehicleapp.HomeScreen.MainActivity;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.View_Cars_Result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;
import apporio.com.vehicleapp.StartUpScreens.SplashActivity;

/**
 * Created by admin on 11/19/2015.
 */
public class Parsing_for_ViewCars {

    public static List<View_Cars_Result.CarTypeDetails> carlist;
    public static ArrayList<String> carId = new ArrayList<String>();
    public static ArrayList<String> carName = new ArrayList<String>();
    public static ArrayList<String> carImage = new ArrayList<String>();
    public static String cartypename;
    public static RequestQueue queue;
    public static StringRequest request;

    public static ProgressDialog pDialog;


    static int[] c={1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    public static void parseCars(final Context carview){

        String viewCarURL= all_Api_s.Viewcars;

        pDialog = new ProgressDialog(carview);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        queue= VolleySingleton.getInstance(carview).getRequestQueue();

        request=new StringRequest(Request.Method.GET, viewCarURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "view car response" + response);
                pDialog.dismiss();
                try {
                    GsonBuilder builder=new GsonBuilder();
                    Gson gson=builder.create();

                    carId.clear();
                    carName.clear();
                    carImage.clear();

                    View_Cars_Result carTypeResult=new View_Cars_Result();
                    carTypeResult=gson.fromJson(response,View_Cars_Result.class);
                    String carresult=carTypeResult.result;

                    if (carresult.toString().equals("1")){
                        carlist=carTypeResult.cityDetailsList;

                        for (int i = 0; i <carlist.size(); i++) {
                            carId.add(carlist.get(i).car_type_id);
                            carName.add(carlist.get(i).car_type_name);
                            carImage.add(carlist.get(i).car_type_image);
                        }
                        MainActivity.carid=carId.get(0);
                        Parsing_Rate_Card.ParseRateCard(carview, MainActivity.carid);
                        MainActivity.selectedcarname=carName.get(0);
//                        MainActivity m= new MainActivity();
//                        m.createhori();
//                        m.checkGps();
                        Log.e("car names", "" + carName);

                        if (SplashActivity.ParseTest.equals("")){
                          carview.startActivity(new Intent(carview, MainActivity.class));
                        }else {
                           // Toast.makeText(carview, "not on main", Toast.LENGTH_SHORT).show();
                        }




                    }else {

                    }


                } catch (Exception e) {
                    pDialog.dismiss();
                    Log.e("error", "view car error" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                if(error instanceof NoConnectionError) {
                    Toast.makeText(carview, "Plz Check Your Internet !!!", Toast.LENGTH_SHORT).show();
                }

                Log.e("error", "view car error" + error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue.add(request);

    }
    }

