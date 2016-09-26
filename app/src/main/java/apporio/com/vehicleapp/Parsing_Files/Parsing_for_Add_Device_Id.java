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
import apporio.com.vehicleapp.Setter_Getter_Files.AddDeviceResult;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 2/16/2016.
 */
public class Parsing_for_Add_Device_Id {

    public static RequestQueue queue;
    public static StringRequest request;

    public static void Add_Device_ID(final Context DeviceIDActivity,String User_id,String D_id,String flag){

     String AddDeviceURL= all_Api_s.AddDeviceId.concat(User_id).concat(all_Api_s.AddDeviceId1).concat(D_id).concat(all_Api_s.AddDeviceId2).concat(flag);
      AddDeviceURL=AddDeviceURL.replace(" ","%20");
        Log.e("Add device id url", "" + AddDeviceURL);

        queue = VolleySingleton.getInstance(DeviceIDActivity).getRequestQueue();
        request = new StringRequest(Request.Method.GET, AddDeviceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("add device", "" + response);
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    AddDeviceResult deviceResult=new AddDeviceResult();
                    deviceResult=gson.fromJson(response,AddDeviceResult.class);

                    if (deviceResult.result.equals("1")){
                      //  Toast.makeText(DeviceIDActivity, "" + deviceResult.msg, Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(DeviceIDActivity, "" + deviceResult.msg, Toast.LENGTH_LONG).show();
                    }


                    //      Log.e(" add emergency", "" + rstatusResultCore.msg);

                } catch (Exception e) {

                    Log.e("Exception", "add device Exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error add device", "" + error);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }
}
