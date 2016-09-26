package apporio.com.vehicleapp.NavigationActivities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lacronicus.easydatastorelib.DatastoreBuilder;
import com.squareup.picasso.Picasso;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.GCMClasses.FCMNotificationIntentService;
import apporio.com.vehicleapp.HomeScreen.MainActivity;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_rating;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.RideInfoResult;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;
import apporio.com.vehicleapp.Singleton.VolleySingleton;
import apporio.com.vehicleapp.views.CustomRatingBarGreen;

public class BillActivity extends Activity {

    TextView amount,RidedateTime,pickaddress,dropaddress,ok,howww;
    ImageView driverimage;
    public static CustomRatingBarGreen ratingBarGreen;
    MyDatastore datastore;
   LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        amount=(TextView)findViewById(R.id.totalamount);
        RidedateTime=(TextView)findViewById(R.id.datetimeride);
        pickaddress=(TextView)findViewById(R.id.pickadressText);
        dropaddress=(TextView)findViewById(R.id.dropaddresstext);
        ok=(TextView)findViewById(R.id.completeRide);
        driverimage=(ImageView)findViewById(R.id.Rideimage);
        back = (LinearLayout)findViewById(R.id.billback);
        howww = (TextView)findViewById(R.id.txttt) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        ratingBarGreen = (CustomRatingBarGreen) findViewById(R.id.ratingBar2);

        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(BillActivity.this))
                .create(MyDatastore.class);

        final Intent iii = getIntent();

        if (iii.getStringExtra("from").equals("1")){
            howww.setVisibility(View.GONE);
            ratingBarGreen.setVisibility(View.GONE);
        }else {
            ratingBarGreen.setVisibility(View.VISIBLE);
            howww.setVisibility(View.VISIBLE);
        }


        ViewRideInfo(BillActivity.this , iii.getStringExtra("doneid"));


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (iii.getStringExtra("from").equals("1")){
                    finish();
                }
                else {
                    finish();
                    Intent in = new Intent(BillActivity.this , MainActivity . class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                }


            }
        });


        ratingBarGreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = ratingBarGreen.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int) starsf + 1;
                    ratingBarGreen.setScore(stars);
                    LayerDrawable layerDrawable = (LayerDrawable) ratingBarGreen.getOnScoreChanged();
//                    DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(0)), Color.GRAY);   // Empty star
//                    DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(1)), Color.GREEN); // Partial star
//                    DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), Color.parseColor("#2980b9"));

                    Parsing_for_rating.Parse_Rating(BillActivity.this, datastore.USER_ID().get(), "1", String.valueOf(stars));
                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //toast.cancel();
                        }
                    }, 500);
                    v.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }
                return true;
            }
        });


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = BillActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(BillActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = BillActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public  void ViewRideInfo(final Context RidesInfo, String Rid) {
        String RideInfoURL = all_Api_s.ViewRideInfo.concat(Rid);
        RideInfoURL = RideInfoURL.replace(" ", "%20");
        Log.e("", "" + RideInfoURL);

        RequestQueue queue = VolleySingleton.getInstance(RidesInfo).getRequestQueue();

        StringRequest request = new StringRequest(Request.Method.GET, RideInfoURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "ride info" + response);

                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    RideInfoResult infoResult = new RideInfoResult();
                    infoResult = gson.fromJson(response, RideInfoResult.class);

                    if (infoResult.result.equals("1")) {

                        amount.setText("$ "+infoResult.Message.amount);
                        RidedateTime.setText(infoResult.Message.done_date);
                        pickaddress.setText(infoResult.Message.begin_address);
                        dropaddress.setText(infoResult.Message.end_address);
                        Picasso.with(BillActivity.this).load("http://apporio.in/load_up_app/"+ infoResult.Message.driver_image).into(driverimage);



                    } else {

                    }

                } catch (Exception e) {
                    Log.e("exception", "ride info exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "ride info error" + error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }



}
