package apporio.com.vehicleapp.StartUpScreens;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.lacronicus.easydatastorelib.DatastoreBuilder;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import apporio.com.vehicleapp.Adapters.SplashPagerAdapter;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_ViewCars;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_callSupport;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;
import apporio.com.vehicleapp.logger.Logger;

public class SplashActivity extends Activity {

    ImageView splaseimageView;
    ImageView logo;
    TextView Login,Signup;
    boolean previouslyStarted;
    public  static   SplashActivity contexty;
    long delay=2000;
    GoogleCloudMessaging gcmObj;
    public static String regId = "";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    MyDatastore datastore;
    public  static ViewPager viewPager;
    FrameLayout logout,login;
   // CirclePageIndicator titleIndicator;
    ArrayList<Integer> spimages=new ArrayList<Integer>();

    LayoutInflater inflater;

    View footer_view;

    public static String ParseTest="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        contexty=SplashActivity.this;

        splaseimageView=(ImageView)findViewById(R.id.image);
        logo=(ImageView)findViewById(R.id.applogo);



        logout=(FrameLayout)findViewById(R.id.layoutlogout);
        login=(FrameLayout)findViewById(R.id.layoutlogin);

        viewPager = (ViewPager) findViewById(R.id.sppager);
       // titleIndicator = (CirclePageIndicator)findViewById(R.id.titles);


        spimages.add(0,R.drawable.spimage1);
        spimages.add(1,R.drawable.spimage2);
        spimages.add(2, R.drawable.spimage3);

        viewPager.setAdapter(new SplashPagerAdapter(SplashActivity.this, spimages));
     //   titleIndicator.setViewPager(viewPager);
      //  titleIndicator.setFillColor(getResources().getColor(R.color.color_primary));



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position==2){

                    inflater=(LayoutInflater)contexty.getSystemService(contexty.LAYOUT_INFLATER_SERVICE);

                  footer_view= inflater.inflate(R.layout.splash_footer, null);

                    Login=(TextView)footer_view.findViewById(R.id.logintext);
                    Signup=(TextView)footer_view.findViewById(R.id.signuptext);


                    Login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                    });

                    Signup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                        }
                    });


                    logout.addView(footer_view);

                }

                else {
                logout.removeView(footer_view);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
       // checkGps();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        previouslyStarted = prefs.getBoolean("pref_previously_started", false);

        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(SplashActivity.this))
                .create(MyDatastore.class);


        Parsing_for_callSupport.parseCallSupport(contexty);

      //  Parsing_for_ViewCars.parseCars(contexty);

        checkPlayServices();

        if (!previouslyStarted) {
            ParseTest="1";
            Parsing_for_ViewCars.parseCars(contexty);

            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
            datastore.DEVICE_ID().put("0");
        } else if (previouslyStarted) {

            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);

        }


        YoYo.with(Techniques.Landing)
                .duration(3000)
                .playOn(findViewById(R.id.applogo));

        Timer timer=new Timer();
        work w=new work();
        timer.schedule(w, delay);



    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = SplashActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(SplashActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = SplashActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }



    class work extends TimerTask {


        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!previouslyStarted) {


                    } else if (previouslyStarted) {
                       ParseTest="";
                        Parsing_for_ViewCars.parseCars(contexty);

                       // startActivity(new Intent(SplashActivity.this, MainActivity.class));

                    }

                }
            });


        }
    }

    /////////////////////////////////////  Gcm code

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(SplashActivity.this, "This device doesn't support Play services, App will not get notifications", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        } else {
           // registerInBackground();
          //  Toast.makeText(SplashActivity.this, "This device supports Play services, App will work normally", Toast.LENGTH_LONG).show();
        }
        return true;
    }

//    private void registerInBackground() {
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                String msg = "";
//                try {
//                    if (gcmObj == null) {
//                        gcmObj = GoogleCloudMessaging.getInstance(SplashActivity.this);
//                    }
//                    regId = gcmObj.register(GCMConstants.GOOGLE_PROJ_ID);
//                    msg = "Registration ID :" + regId;
//
//                } catch (IOException ex) {
//                    msg = "Error :" + ex.getMessage();
//                }
//                return msg;
//            }
//
//            @Override
//            protected void onPostExecute(String msg) {
//                if (!TextUtils.isEmpty(regId)) {
//                    //       storeRegIdinSharedPref(applicationContext, regId, emailID);
//                    Logger.e("Registration id on SPLASH SCREEN " + regId, "");
//
//                    Log.e("id in sf",""+datastore.DEVICE_ID().get());
//
//
//                    if (datastore.DEVICE_ID().get().equals(regId)){
//                   //     Toast.makeText(SplashActivity.this, "Same device id", Toast.LENGTH_SHORT).show();
//                    }else {
//                        datastore.DEVICE_ID().put(regId);
//                        Log.e("",""+datastore.DEVICE_ID().get());
//                    }
//
//                } else {
//                    Toast.makeText(SplashActivity.this, "Reg ID Creation Failed.Either you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time." + msg, Toast.LENGTH_LONG).show();
//                }
//            }
//        }.execute(null, null, null);
//    }


}
