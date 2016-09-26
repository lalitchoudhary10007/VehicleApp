package apporio.com.vehicleapp.Parsing_Files;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.FileCache;
import apporio.com.vehicleapp.HomeScreen.MainActivity;
import apporio.com.vehicleapp.HomeScreen.MemoryCache;
import apporio.com.vehicleapp.HomeScreen.Utils;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.DriversResponse;
import apporio.com.vehicleapp.Setter_Getter_Files.DriversResult;
import apporio.com.vehicleapp.Setter_Getter_Files.MyridesResultCore;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 5/16/2016.
 */
public class Parsing_Drivers_Home {

    public static RequestQueue queue;
    public static StringRequest request;

    public static List<DriversResult.driverDetails> driverDetailsList;
    public static ArrayList<String> did=new ArrayList<String>();
    public static ArrayList<String> dlat=new ArrayList<String>();
    public static ArrayList<String> dlong=new ArrayList<String>();
    public static ArrayList<String> dduration=new ArrayList<String>();
    public static ArrayList<String> driver_city_id=new ArrayList<String>();
    public static ArrayList<String> dcarimage=new ArrayList<String>();

    public static ProgressDialog pDialog;


    public static void ParseDrivers(final Context home, String lat,String lng,String cartype){

        String homescrrenURL= all_Api_s.homeScreen.concat(lat).concat(all_Api_s.homeScreen1).concat(lng).concat(all_Api_s.homeScreen2)
                .concat(cartype);
        homescrrenURL=homescrrenURL.replace(" ", "%20");
        Log.e("drivers url", "" + homescrrenURL);

        pDialog = new ProgressDialog(home);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        queue = VolleySingleton.getInstance(home).getRequestQueue();

        request = new StringRequest(Request.Method.GET, homescrrenURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                did.clear();
                dlat.clear();
                dlong.clear();
                dduration.clear();
                driver_city_id.clear();
                dcarimage.clear();

                Log.e("respons home screen", "" + response);

                pDialog.dismiss();

                try {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    DriversResponse response1=new DriversResponse();
                    response1=gson.fromJson(response,DriversResponse.class);
                    Log.e("result of home screen",""+response1.response.result);
                    MainActivity.mGoogleMap.clear();

                    if (response1.response.result.equals("1")){

                        DriversResult driversResult=new DriversResult();
                        driversResult = gson.fromJson(response, DriversResult.class);
                        driverDetailsList=driversResult.response.Message;

                        MainActivity.nopickup.setVisibility(View.GONE);

                        for (int i=0;i<driverDetailsList.size();i++){
                            did.add(driverDetailsList.get(i).driver_id);
                            dlat.add(driverDetailsList.get(i).lat);
                            dlong.add(driverDetailsList.get(i).longg);
                            dduration.add(driverDetailsList.get(i).duration);
                            driver_city_id.add(driverDetailsList.get(i).city_id);
                            dcarimage.add(driverDetailsList.get(i).car_type_image);
                            Log.e("cariimage",""+dcarimage);
                        }

                        MainActivity.updateTime.setText(dduration.get(0));


                    }else {
                       MainActivity.nopickup.setVisibility(View.VISIBLE);
                       MainActivity.updateTime.setText("0 min");
                    }


                    for (int k = 0; k <did.size(); k++) {
                        //Bitmap bmp = tc.makeIcon(""+ name.get(k));

//                        MainActivity.mGoogleMap.addMarker(new MarkerOptions()
//                                .position(new LatLng(Double.parseDouble((dlat.get(k))), Double.parseDouble((dlong.get(k)).toString())))
//                                .title("Cab" + did.get(k)).icon(BitmapDescriptorFactory.fromBitmap(getBitmap("http://keshavgoyal.com/load_up_app/"+dcarimage.get(k), home))));
//                        MainActivity.mGoogleMap.setMyLocationEnabled(true);

                        MainActivity.mGoogleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble((dlat.get(k))), Double.parseDouble((dlong.get(k)).toString())))
                                .title("Cab" + did.get(k)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_img)));
                        MainActivity.mGoogleMap.setMyLocationEnabled(true);


                    }

                } catch (Exception e) {

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

        queue.add(request);

    }



    public static Bitmap getBitmap(String url,Context context)
    {
        FileCache fileCache=new FileCache(context);
        MemoryCache memoryCache=new MemoryCache();
        File f=fileCache.getFile(url);
        //from SD cache
        //CHECK : if trying to decode file which not exist in cache return null
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;
        // Download image file from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            // Constructs a new FileOutputStream that writes to file
            // if file not exist then it will create file
            OutputStream os = new FileOutputStream(f);
            // See Utils class CopyStream method
            // It will each pixel from input stream and
            // write pixels to output stream (file)
            Utils.CopyStream(is, os);
            os.close();
            conn.disconnect();
            //Now file created and going to resize file with defined height
            // Decodes image and scales it to reduce memory consumption
            b = decodeFile(f);
            return bitmap;

        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }




    private static Bitmap decodeFile(File f){

        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();
            //Find the correct scale value. It should be the power of 2.
            // Set width/height of recreated image
            final int REQUIRED_SIZE=85;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with current scale values
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;

        } catch (FileNotFoundException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }






}
