package apporio.com.vehicleapp.Parsing_Files;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import apporio.com.vehicleapp.Adapters.ShipMentAdapter;
import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.Helper;
import apporio.com.vehicleapp.HomeScreen.MainActivity;
import apporio.com.vehicleapp.HomeScreen.ShipmentActivity;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.DriversResponse;
import apporio.com.vehicleapp.Setter_Getter_Files.DriversResult;
import apporio.com.vehicleapp.Setter_Getter_Files.View_Shipment_Only_Result;
import apporio.com.vehicleapp.Setter_Getter_Files.View_Shipment_Result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by apporio5 on 13-07-2016.
 */
public class Parsing_for_view_shipment {

    public static RequestQueue queue;
    public static StringRequest request;

    public static ProgressDialog pDialog;

    public static List<View_Shipment_Result.Shipment_Details> shipDetailsList;
    public static ArrayList<String> sid=new ArrayList<String>();
    public static ArrayList<String> sweight=new ArrayList<String>();
    public static ArrayList<String> simage=new ArrayList<String>();
    public static ArrayList<String> squantity=new ArrayList<String>();
    public static ArrayList<String> sinst=new ArrayList<String>();

    public static void ViewShipment(final Context home){

        String viewshipURL= all_Api_s.View_Shipment;

        viewshipURL=viewshipURL.replace(" ", "%20");
        Log.e("shipment url", "" + viewshipURL);

        pDialog = new ProgressDialog(home);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        queue = VolleySingleton.getInstance(home).getRequestQueue();

        request = new StringRequest(Request.Method.GET, viewshipURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                sid.clear();
                simage.clear();
                squantity.clear();
                sweight.clear();
                sinst.clear();

                Log.e("respons home screen", "" + response);

                pDialog.dismiss();

                try {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    View_Shipment_Only_Result response1=new View_Shipment_Only_Result();
                    response1=gson.fromJson(response,View_Shipment_Only_Result.class);



                    if (response1.result.toString().equals("1")){

                        ShipmentActivity.Add_Shipment.setVisibility(View.GONE);
                        ShipmentActivity. ShipListlayout.setVisibility(View.VISIBLE);

                        View_Shipment_Result result=new View_Shipment_Result();
                        result = gson.fromJson(response, View_Shipment_Result.class);
                        shipDetailsList=result.Message;



                        for (int i=0;i<shipDetailsList.size();i++){
                            sid.add(shipDetailsList.get(i).shipment_id);
                            sweight.add(shipDetailsList.get(i).shipment_weight);
                            squantity.add(shipDetailsList.get(i).shipment_quantity);
                            simage.add(shipDetailsList.get(i).shipment_image);
                            sinst.add(shipDetailsList.get(i).shipment_spcl_instruction);
                        }

//                        ShipMentAdapter ad=new ShipMentAdapter(home, simage,sweight,sinst,squantity,sid);
//                        ShipmentActivity.ShipListView.setAdapter(ad);
//                        ShipmentActivity.setListViewHeightBasedOnChildren(ShipmentActivity.ShipListView);


                    }else {
                       ShipmentActivity.Add_Shipment.setVisibility(View.VISIBLE);
                       ShipmentActivity.ShipListlayout.setVisibility(View.GONE);
                    }



                } catch (Exception e) {
                   pDialog.dismiss();
                    Log.e("Exception", "home screen Exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error in home screen", "" + error);
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
