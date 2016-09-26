package apporio.com.vehicleapp.Parsing_Files;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.Fragments.CurrentOrderFragment;
import apporio.com.vehicleapp.HomeScreen.DriverdetailsActivity;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.MyRidesResult;
import apporio.com.vehicleapp.Setter_Getter_Files.MyridesResultCore;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by apporio5 on 21-07-2016.
 */
public class Parsing_for_currentOrder {


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
    public static ArrayList<String> RideStatus = new ArrayList<String>();
    public static ArrayList<String> driverStatus = new ArrayList<String>();
public static String driverstat;
  //  public static ArrayList<MyridesResultCore.MyridesDetailsCore.receipent_details> Receipent = new ArrayList<>();

    public static void parseMyRidess(final Context Viewridesctivity, String usid, String Rsatus) {

        String ViewrideURL = all_Api_s.myRides.concat(usid).concat(all_Api_s.myRides1).concat(Rsatus);
        ViewrideURL = ViewrideURL.replace(" ", "%20");
        Log.e("my rides url",""+ViewrideURL);

        queue = VolleySingleton.getInstance(Viewridesctivity).getRequestQueue();

        request = new StringRequest(Request.Method.GET, ViewrideURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("respons view my rides", "" + response);
                CurrentOrderFragment.barinallrides.setVisibility(View.GONE);

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
                    RideStatus.clear();
                 //   Receipent.clear();
                    driverStatus.clear();

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    MyRidesResult resultCore1 = new MyRidesResult();
                    resultCore1 = gson.fromJson(response, MyRidesResult.class);
                    Log.e("result of my rides", "" + resultCore1.result);


                    if (resultCore1.result.equals("1")) {
                        CurrentOrderFragment.Norides.setVisibility(View.GONE);
                        CurrentOrderFragment.statuslayout.setVisibility(View.VISIBLE);
                        CurrentOrderFragment.receipentLayout.setVisibility(View.VISIBLE);

                        MyridesResultCore resultCore = new MyridesResultCore();
                        resultCore = gson.fromJson(response, MyridesResultCore.class);
                    //    myridesDetailsCores = resultCore.Message;


                        for (int i = 0; i < resultCore.getMessage().size(); i++) {
                            pickUPtime.add(resultCore.getMessage().get(i).getPickup_time());
                            pickAddress.add(resultCore.getMessage().get(i).getPickup_address());
                            pickUPdate.add(resultCore.getMessage().get(i).getPickup_date());
                            rideid.add(resultCore.getMessage().get(i).getRide_id());
                            carimages.add(resultCore.getMessage().get(i).getCar_type_image());
                            carnames.add(resultCore.getMessage().get(i).getCar_type_name());
                            carnumbers.add(resultCore.getMessage().get(i).getCar_number());

                            driverImages.add(resultCore.getMessage().get(i).getDriver_image());

                         //   amount.add(myridesDetailsCores.get(i).done_ride.amount);
                          //  dropAddress.add(myridesDetailsCores.get(i).drop_address);
                         //   RideStatus.add(myridesDetailsCores.get(i).ride_status);
                         //   Receipent.add(myridesDetailsCores.get(i).receipent);
                         //   driverStatus.add(myridesDetailsCores.get(i).driver_status);
                      }

                        CurrentOrderFragment.rname.setText(resultCore.getMessage().get(0).getReceipent().getReceipent_name());
                        CurrentOrderFragment.raddress.setText(resultCore.getMessage().get(0).getReceipent().getReceipent_address());
                        CurrentOrderFragment.rphone.setText("PH - "+resultCore.getMessage().get(0).getReceipent().getReceipent_phonenumber());
                        Picasso.with(Viewridesctivity).load("http://apporio.in/load_up_app/"+resultCore.getMessage().get(0).getDriver_image())
                                .error(R.drawable.defaultprof)
                                .placeholder(R.drawable.defaultprof)
                                .into(CurrentOrderFragment.shipimage);
                        driverstat = resultCore.getMessage().get(0).getDriver_status();

                        CurrentOrderFragment.receipentLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (driverstat.equals("0")){
                                    Toast.makeText(Viewridesctivity, "Driver Is Not Available Yet !!!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Intent iii = new Intent(Viewridesctivity,DriverdetailsActivity.class);
                                    iii.putExtra("ride",rideid.get(0));
                                    Viewridesctivity. startActivity(iii);
                                }


                            }
                        });


                        Log.e("driver status",""+resultCore.getMessage().get(0).getDriver_status());

                        if (resultCore.getMessage().get(0).getDriver_status().equals("0")){





                        }


                     else if (resultCore.getMessage().get(0).getDriver_status().equals("1")){


                          CurrentOrderFragment.second_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                          CurrentOrderFragment.second_first_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_s_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_t_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_ago.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.third_Frame.setBackgroundResource(R.drawable.round_layout_grey);
                          CurrentOrderFragment.forth_Frame.setBackgroundResource(R.drawable.round_layout_grey);
                          CurrentOrderFragment.fifth_Frame.setBackgroundResource(R.drawable.round_layout_grey);
                          CurrentOrderFragment.third_first_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.third_t_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.third_s_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.third_ago.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.forth_first_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.forth_s_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.forth_t_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.forth_ago.setTextColor(Color.parseColor("#666666"));

                          CurrentOrderFragment.fifth_first_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.fifth_s_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.fifth_t_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.fifth_ago.setTextColor(Color.parseColor("#666666"));


                            CurrentOrderFragment.first_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.first_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_ago.setTextColor(Color.parseColor("#0097e5"));




                      }else if (resultCore.getMessage().get(0).getDriver_status().equals("2")){

                          CurrentOrderFragment.first_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                          CurrentOrderFragment.second_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                          CurrentOrderFragment.first_first_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.first_t_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.first_s_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.first_ago.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_first_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_s_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_t_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_ago.setTextColor(Color.parseColor("#0097e5"));

                          CurrentOrderFragment.third_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                          CurrentOrderFragment.forth_Frame.setBackgroundResource(R.drawable.round_layout_grey);
                          CurrentOrderFragment.fifth_Frame.setBackgroundResource(R.drawable.round_layout_grey);
                          CurrentOrderFragment.third_first_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.third_t_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.third_s_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.third_ago.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.forth_first_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.forth_s_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.forth_t_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.forth_ago.setTextColor(Color.parseColor("#666666"));

                          CurrentOrderFragment.fifth_first_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.fifth_s_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.fifth_t_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.fifth_ago.setTextColor(Color.parseColor("#666666"));

                      }else if (resultCore.getMessage().get(0).getDriver_status().equals("3")){

                          CurrentOrderFragment.first_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                          CurrentOrderFragment.second_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                          CurrentOrderFragment.first_first_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.first_t_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.first_s_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.first_ago.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_first_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_s_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_t_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.second_ago.setTextColor(Color.parseColor("#0097e5"));

                          CurrentOrderFragment.third_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                          CurrentOrderFragment.forth_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                          CurrentOrderFragment.fifth_Frame.setBackgroundResource(R.drawable.round_layout_grey);
                          CurrentOrderFragment.third_first_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.third_t_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.third_s_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.third_ago.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.forth_first_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.forth_s_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.forth_t_txt.setTextColor(Color.parseColor("#0097e5"));
                          CurrentOrderFragment.forth_ago.setTextColor(Color.parseColor("#0097e5"));

                          CurrentOrderFragment.fifth_first_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.fifth_s_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.fifth_t_txt.setTextColor(Color.parseColor("#666666"));
                          CurrentOrderFragment.fifth_ago.setTextColor(Color.parseColor("#666666"));

                      }else if (resultCore.getMessage().get(0).getDriver_status().equals("4")){



                            CurrentOrderFragment.first_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.first_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_ago.setTextColor(Color.parseColor("#0097e5"));



                      }

                        else if (resultCore.getMessage().get(0).getDriver_status().equals("5")){


                            CurrentOrderFragment.first_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.second_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.first_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_ago.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.second_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.second_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.second_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.second_ago.setTextColor(Color.parseColor("#0097e5"));

                            CurrentOrderFragment.third_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.forth_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.fifth_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.third_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.third_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.third_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.third_ago.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.forth_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.forth_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.forth_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.forth_ago.setTextColor(Color.parseColor("#0097e5"));

                            CurrentOrderFragment.fifth_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.fifth_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.fifth_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.fifth_ago.setTextColor(Color.parseColor("#0097e5"));


                        }




                       else if (resultCore.getMessage().get(0).getDriver_status().equals("6")){
                            CurrentOrderFragment.first_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.second_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.first_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.first_ago.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.second_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.second_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.second_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.second_ago.setTextColor(Color.parseColor("#0097e5"));

                            CurrentOrderFragment.third_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.forth_Frame.setBackgroundResource(R.drawable.round_layout_blue);
                            CurrentOrderFragment.fifth_Frame.setBackgroundResource(R.drawable.round_layout_grey);
                            CurrentOrderFragment.third_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.third_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.third_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.third_ago.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.forth_first_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.forth_s_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.forth_t_txt.setTextColor(Color.parseColor("#0097e5"));
                            CurrentOrderFragment.forth_ago.setTextColor(Color.parseColor("#0097e5"));

                            CurrentOrderFragment.fifth_first_txt.setTextColor(Color.parseColor("#666666"));
                            CurrentOrderFragment.fifth_s_txt.setTextColor(Color.parseColor("#666666"));
                            CurrentOrderFragment.fifth_t_txt.setTextColor(Color.parseColor("#666666"));
                            CurrentOrderFragment.fifth_ago.setTextColor(Color.parseColor("#666666"));
                        }





                    } else {
                        CurrentOrderFragment.Norides.setVisibility(View.VISIBLE);
                        CurrentOrderFragment.statuslayout.setVisibility(View.GONE);
                        CurrentOrderFragment.receipentLayout.setVisibility(View.GONE);
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
        CurrentOrderFragment.barinallrides.setVisibility(View.VISIBLE);

    }




}
