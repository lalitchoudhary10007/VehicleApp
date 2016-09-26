package apporio.com.vehicleapp.HomeScreen;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Hashtable;

import apporio.com.vehicleapp.NavigationActivities.ConnectionDetector;
import apporio.com.vehicleapp.Parsing_Files.Parsing_Driver_Detail;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_CancelRide;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.views.CustomRatingBarGreen;

public class DriverdetailsActivity extends Activity {


    public static int distance;
    public static Marker now;
    public static MapView mapView;
    public static GoogleMap map;
    public static Circle mapCircle;
    public static GPStracker gps;
    public static ProgressBar pb;

    public static Boolean isInternetPresent = false;
     public static ConnectionDetector cd;
    public static ImageView back;
    public static Double currentlat, currentlong;
    public static ArrayList<String> name = new ArrayList<String>();
    public static ArrayList<String[]> latlng = new ArrayList<>();
    public static ArrayList<String> latitude = new ArrayList<>();
    public static ArrayList<String> longitude = new ArrayList<>();
    public static String name1[] = {"1"};
  //  public static String lat2[] = {"28.595520"};
  //  public static String long2[] = {"77.23195220000001"};
    public static FrameLayout call;
    public static TextView cancelride, text22;
    // private ImageLoader imageLoader;
    public static View layout3;
    public static Typeface font;
    // private DisplayImageOptions options;
    RatingBar rb;
    public static Context ctc;
    private Marker marker;
    LayoutInflater inflater3;
    private Hashtable<String, String> markers;

    public static TextView drivernme, drivercarname, drivercarno;
    public static ImageView driverimage;
   public static String time;
    public static String driverid;
    public static String useridinprefs;
    public static String star;
    public static String im ;
    public static DriverdetailsActivity driverdetailsactivity;

    public static CustomRatingBarGreen ratingBarGreen;
    public static String callorsms="1";
    String ratedValue;
    public static LinearLayout driverDetailLayout ;
    LinearLayout backLayout;
   public static FrameLayout waitinglayout;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    public static String rideid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverdetails);
        driverdetailsactivity = DriverdetailsActivity.this;

        backLayout=(LinearLayout)findViewById(R.id.driverdetailback);
        driverDetailLayout = (LinearLayout)findViewById(R.id.detailLayout);
        ratingBarGreen = (CustomRatingBarGreen) findViewById(R.id.ratingBar1);
        drivernme = (TextView) findViewById(R.id.drivername);
        drivercarname = (TextView) findViewById(R.id.drivercarname);
        drivercarno = (TextView) findViewById(R.id.drivercarno);
        driverimage = (ImageView) findViewById(R.id.drivercircularimage);
        call=(FrameLayout)findViewById(R.id.Calltodriver);

      //  waitinglayout = (FrameLayout)findViewById(R.id.progresslayout);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            //    PickImageActivity.pickImageActivity.finish();
            }
        });

//        waitinglayout.setVisibility(View.VISIBLE);
//        driverDetailLayout.setVisibility(View.GONE);

//        Intent ii=getIntent();
//        time=ii.getStringExtra("duration");
//        driverid=ii.getStringExtra("id");
//        currentlat= Double.valueOf(ii.getStringExtra("driverlat"));
//        currentlong=Double.valueOf(ii.getStringExtra("driverlong"));
//        drivernme.setText(ii.getStringExtra("name"));
//        drivercarno.setText(ii.getStringExtra("carno"));
//        drivercarname.setText("Scorpio");
//         im=ii.getStringExtra("driverimage");
//        star=ii.getStringExtra("star");
//        ratingBarGreen.setScore(Float.parseFloat(star));
//         Picasso.with(driverdetailsactivity).load("http://keshavgoyal.com/load_up_app/"+im)
//                 .error(R.drawable.defaultprof)
//                 .placeholder(R.drawable.defaultprof)
//                 .into(driverimage);
//        Log.e("", "" + drivernme);


//        time="1 min.";
//        driverid="1";
//        currentlat=Double.valueOf("28.4120298");
//        currentlong=Double.valueOf("77.0433618");
//        drivernme.setText("Apporio");
//        drivercarno.setText("Dl 01 1234");
//        drivercarname.setText("Scorpio");
//        ratingBarGreen.setScore(Float.parseFloat("3"));
//        driverimage.setImageResource(R.drawable.defaultprof);


        Intent ii=getIntent();
      rideid =   ii.getStringExtra("ride");

       Parsing_Driver_Detail.DriverDetail(DriverdetailsActivity.this,rideid);


        ctc = getApplicationContext();
        pb = (ProgressBar) findViewById(R.id.progressBarinsplash);
        gps = new GPStracker(ctc);
        inflater3 = getLayoutInflater();
        cd = new ConnectionDetector(ctc);
        mapView = (MapView) findViewById(R.id.mapgh);


        mapView.onCreate(savedInstanceState);



        cancelride = (TextView) findViewById(R.id.cancelRide);
//        for (int i = 0; i < name1.length; i++) {
//            name.add(name1[i]);
//            latitude.add(lat2[i]);
//            longitude.add(long2[i]);
//
//        }


        font = Typeface.createFromAsset(ctc.getAssets(), "font/Quicksand_Book.otf");
       // currentlat = gps.getLatitude();
       // currentlong = gps.getLongitude();

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showcallSmsdialog();
            }
        });


        cancelride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCancelRidedialog();

            }
        });



        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            try {

                String permission = "android.permission.READ_PHONE_STATE";

                int permissionCheck = ContextCompat.checkSelfPermission(DriverdetailsActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

                if (ContextCompat.checkSelfPermission(DriverdetailsActivity.this,
                        Manifest.permission_group.LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(DriverdetailsActivity.this,
                            Manifest.permission_group.LOCATION)) {
                        ActivityCompat.requestPermissions(DriverdetailsActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        Toast.makeText(getApplicationContext(), "Please allow phone permission to receive push notifications !!!", Toast.LENGTH_LONG).show();
                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.


                    } else {

                        // No explanation needed, we can request the permission.


//                        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//                        String dd = mngr.getDeviceId();

                       // parsingfornotification.parsing(MainActivity.this, "" + dd, regId);
                        //Toast.makeText(MainActivity.this, "not mm", Toast.LENGTH_SHORT).show();
                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.

                        //getthetracker();
                    }
                }
                else {
                   // getthetracker();
                }
            } catch (Exception e) {
                Log.e("ghghghhg", "" + e);
            }
        }
        else {
            ActivityCompat.requestPermissions(DriverdetailsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
           //getthetracker();
        }

        // parsingfornotification.parsing(MainActivity.this,regId,dd);


    }



//    private boolean canAccessLocation() {
//        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
//    }



//    @TargetApi(Build.VERSION_CODES.M)
//    private boolean hasPermission(String perm) {
//
//        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(DriverdetailsActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
//                    TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//                    String dd = mngr.getDeviceId();
                    Parsing_Driver_Detail.DriverDetail(DriverdetailsActivity.this,rideid);
                   // getthetracker();
//                    parsingfornotification.parsing(MainActivity.this, ""+dd, regId);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(DriverdetailsActivity.this, "permission denied", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public static void getthetracker(double lat ,double lng) {


        MapsInitializer.initialize(ctc);
        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctc)) {
            case ConnectionResult.SUCCESS:

                //Toast.makeText(ctc, "tracker", Toast.LENGTH_SHORT).show();
                // Gets to GoogleMap from the MapView and does initialization stuff
                if (mapView != null) {
                    map = mapView.getMap();
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    map.setMyLocationEnabled(true);
                    gps = new GPStracker(ctc, map);
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(lat,
                                    lng));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

                    map.moveCamera(center);
                    map.animateCamera(zoom);


                   // for (int k = 0; k < name.size(); k++) {
                        //Bitmap bmp = tc.makeIcon(""+ name.get(k));

                        now = map.addMarker(new MarkerOptions()
                                .position(new LatLng(lat,lng))
                                .title(time).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
                        map.setMyLocationEnabled(true);
                        String[] parts = now.getId().split("m");
                        String part1 = parts[1];
                        //Toast.makeText(getApplicationContext(),""+part1,Toast.LENGTH_SHORT).show();



                         now.showInfoWindow();
                  //  }

                 //  animateMarker(now1, new LatLng(currentlat, currentlong),true);
                   // now.showInfoWindow();

               isInternetPresent =cd.isConnectingToInternet();

                    // check for Internet status
                    if (isInternetPresent) {

                    } else {
//                         Internet connection is not present
//                         Ask user to connect to Internet
                      Toast.makeText(ctc, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(ctc, "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(ctc, "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(ctc, GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctc), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onResume() {
        //Toast.makeText(getActivity(), "resume", Toast.LENGTH_SHORT).show();
        mapView.onResume();
        super.onResume();
      Parsing_Driver_Detail.DriverDetail(DriverdetailsActivity.this,rideid);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = DriverdetailsActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(DriverdetailsActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = DriverdetailsActivity.this.getWindow();
            //  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    public  void showCancelRidedialog() {

        final Dialog dialog = new Dialog(DriverdetailsActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=dialog.getWindow();
        dialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogforlogout);
        TextView Title=(TextView)dialog.findViewById(R.id.headingLogout);
        Title.setText("Cancel Ride");
        TextView msg=(TextView)dialog.findViewById(R.id.msggRide);
        msg.setText("Are You sure want to cancel Ride?");
        TextView yes = (TextView)dialog.findViewById(R.id.yes);
        TextView no = (TextView)dialog.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Parsing_for_CancelRide.CancelRide(DriverdetailsActivity.this, ShipmentActivity.rideidbook, "0","PN4");
                dialog.dismiss();
            //startActivity(new Intent(DriverdetailsActivity.this,MainActivity.class));


            }
        });

        no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showcallSmsdialog(){

        final Dialog dialog = new Dialog(DriverdetailsActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=dialog.getWindow();
        dialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogue_for_call_sms);
        LinearLayout call=(LinearLayout)dialog.findViewById(R.id.calllayout);
        LinearLayout sms=(LinearLayout)dialog.findViewById(R.id.smslayout);

        final FrameLayout callFrame=(FrameLayout)dialog.findViewById(R.id.callframe);
        final FrameLayout smsFrame=(FrameLayout)dialog.findViewById(R.id.smsframe);

        TextView yes = (TextView)dialog.findViewById(R.id.yes);
        TextView cancel = (TextView)dialog.findViewById(R.id.no);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               callorsms="1";
            callFrame.setBackground(getResources().getDrawable(R.drawable.white_stroke));
            smsFrame.setBackground(getResources().getDrawable(R.drawable.lightblacksolidbg));
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callorsms="2";
                smsFrame.setBackground(getResources().getDrawable(R.drawable.white_stroke));
                callFrame.setBackground(getResources().getDrawable(R.drawable.lightblacksolidbg));
            }
        });


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Parsing_for_ChangeRideStatus.parseChangeStatus(Driverdetailsactivity.this, Parsing_for_RideNowConfirm.rideidbook, "0");

                if (callorsms.equals("1")){

                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("tel:" + ShipmentActivity.driverphoneno1));
                    ctc.startActivity(intent);


                }else if (callorsms.equals("2")){

                  sendSMS();
                }


                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                dialog.dismiss();
            }
        });

        dialog.show();

    }


    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra("address", ShipmentActivity.driverphoneno1);
          //  sendIntent.putExtra(Intent.EXTRA_TEXT, "sms to driver " + "" + "123456789");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        }
        else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
           // smsIntent.putExtra("sms_body","sms to driver "+""+"123456789");
            smsIntent.putExtra("address", ShipmentActivity.driverphoneno1);
            startActivity(smsIntent);
        }
    }





}


