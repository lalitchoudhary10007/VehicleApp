package apporio.com.vehicleapp.Parsing_Files;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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

import apporio.com.vehicleapp.Adapters.Myallridesadapter;
import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.Fragments.PastOrderFragment;
import apporio.com.vehicleapp.NavigationActivities.BillActivity;
import apporio.com.vehicleapp.NavigationActivities.MyRidesActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.MyRidesResult;
import apporio.com.vehicleapp.Setter_Getter_Files.MyridesResultCore;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 11/21/2015.
 */
public class Parsing_for_ViewMyrides {


   //public static Context aContext;
    public static RequestQueue queue;
    public static StringRequest request;
   // public static StringBuilder str;

   // public static List<MyridesResultCore.MyridesDetailsCore> myridesDetailsCores;
    
    public static ArrayList<String> carimages=new ArrayList<String>();
    public static ArrayList<String> carnames=new ArrayList<String>();
    public static ArrayList<String> carnumbers=new ArrayList<String>();
    public static ArrayList<String> pickUPtime = new ArrayList<String>();
    public static ArrayList<String> pickAddress = new ArrayList<String>();
    public static ArrayList<String> dropAddress = new ArrayList<String>();
    public static ArrayList<String> pickUPdate = new ArrayList<String>();
    public static ArrayList<String> rideid = new ArrayList<String>();
    public static ArrayList<String> driverImages = new ArrayList<String>();
    public static ArrayList<String> amount = new ArrayList<String>();



    public static void parseMyRides(final Context Viewridesctivity, String usid,String Rsatus) {

        String ViewrideURL = all_Api_s.myRides.concat(usid).concat(all_Api_s.myRides1).concat(Rsatus);
        ViewrideURL = ViewrideURL.replace(" ", "%20");
        Log.e("my rides url",""+ViewrideURL);

        queue = VolleySingleton.getInstance(Viewridesctivity).getRequestQueue();

        request = new StringRequest(Request.Method.GET, ViewrideURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("respons view my rides", "" + response);
               PastOrderFragment.barinallrides.setVisibility(View.GONE);

                try {
                    carimages.clear();
                    dropAddress.clear();
                    carnumbers.clear();
                    driverImages.clear();
                    carnames.clear();
                    pickUPdate.clear();
                    pickAddress.clear();
                    pickUPtime.clear();
                    rideid.clear();
                    amount.clear();

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    MyRidesResult resultCore1 = new MyRidesResult();
                    resultCore1 = gson.fromJson(response, MyRidesResult.class);
                    Log.e("result of my rides", "" + resultCore1.result);


                    if (resultCore1.result.equals("1")) {
                        PastOrderFragment.Norides.setVisibility(View.GONE);
                        PastOrderFragment.RidesList.setVisibility(View.VISIBLE);

                        MyridesResultCore resultCore = new MyridesResultCore();
                        resultCore = gson.fromJson(response, MyridesResultCore.class);
                       // myridesDetailsCores = resultCore.Message;


                        for (int i = 0; i < resultCore.getMessage().size(); i++) {
                            pickUPtime.add(resultCore.getMessage().get(i).getPickup_time());
                            pickAddress.add(resultCore.getMessage().get(i).getPickup_address());
                            pickUPdate.add(resultCore.getMessage().get(i).getPickup_date());
                            rideid.add(resultCore.getMessage().get(i).getRide_id());
                            carimages.add(resultCore.getMessage().get(i).getCar_type_image());
                            carnames.add(resultCore.getMessage().get(i).getCar_type_name());
                            carnumbers.add(resultCore.getMessage().get(i).getCar_number());

                            driverImages.add(resultCore.getMessage().get(i).getDriver_image());
                             amount.add(resultCore.getMessage().get(i).getDone_ride());
                             dropAddress.add(resultCore.getMessage().get(i).getDrop_address());
                            Log.e("amount", "" + amount);
                            Log.e("drop address",""+dropAddress);

                        }
                        PastOrderFragment.RidesList.setAdapter(new Myallridesadapter(Viewridesctivity,carnames,pickUPdate,pickAddress,dropAddress,carimages,driverImages,amount,carnumbers));

                        PastOrderFragment.RidesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent ii = new Intent(Viewridesctivity , BillActivity.class);
                                ii.putExtra("doneid",amount.get(position));
                                ii.putExtra("from","1");
                                Viewridesctivity.startActivity(ii);
                            }
                        });



                    } else {
                        PastOrderFragment.Norides.setVisibility(View.VISIBLE);
                        PastOrderFragment.RidesList.setVisibility(View.GONE);
                    }



                } catch (Exception e) {

                    Log.e("Exception", "view my rides Exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error in view my rides", "" + error);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
        PastOrderFragment.barinallrides.setVisibility(View.VISIBLE);

    }



//    public static class GetLocationAsync extends AsyncTask<String, Void, String> {
//
//        private Geocoder geocoder;
//        private List<Address> addresses;
//
//        // boolean duplicateResponse;
//        double x, y;
//        StringBuilder str;
//
//        public GetLocationAsync(double latitude, double longitude) {
//            // TODO Auto-generated constructor stub
//
//            x = latitude;
//            y = longitude;
//        }
//
//        @Override
//        protected void onPreExecute() {
//           // Address.setText(" Getting location ... ");
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            try {
//                geocoder = new Geocoder(Myridesactivity.myridesactivity, Locale.ENGLISH);
//                addresses = geocoder.getFromLocation(x, y, 1);
//                str = new StringBuilder();
//                if (geocoder.isPresent()) {
//                    Address returnAddress = addresses.get(0);
//
//                    String localityString = returnAddress.getLocality();
//                    String city = returnAddress.getCountryName();
//                    String region_code = returnAddress.getCountryCode();
//                    String zipcode = returnAddress.getPostalCode();
//
//                    str.append(localityString + "");
//                    str.append(city + "" + region_code + "");
//                    str.append(zipcode + "");
//
//                    myaddresses.add(str.toString());
//
//                    Log.e("Exception", "my all addresss" + myaddresses);
//
//                }
//
//                else {
//                }
//            } catch (Exception e) {
//                Log.e("tag", ""+e);
//            }
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            try {
//
//
////                Address.setText(addresses.get(0).getAddressLine(0)+", "
////                        + addresses.get(0).getAddressLine(1) + " ");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//
//        }
//    }





//public static void getaddress(Context my,String lat,String lng){
//    Geocoder geocoder = new Geocoder(my, Locale.ENGLISH);
//
//    try {
//        List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lng), 1);
//
//        if(addresses != null) {
//            Address returnedAddress = addresses.get(0);
//            StringBuilder strReturnedAddress = new StringBuilder("");
//            for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
//
//                strReturnedAddress.append(returnedAddress.getAddressLine(i));
//
//            }
//
//            myaddresses.add(strReturnedAddress.toString());
//
//           // strReturnedAddress="".toString();
//           Toast.makeText(my,""+myaddresses,Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText(my,"no address returned",Toast.LENGTH_LONG).show();
//        }
//    } catch (IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//        Toast.makeText(my,"can not get address",Toast.LENGTH_LONG).show();
//    }
//
//}



}

