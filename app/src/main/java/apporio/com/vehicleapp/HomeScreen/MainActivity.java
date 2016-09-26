package apporio.com.vehicleapp.HomeScreen;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lacronicus.easydatastorelib.DatastoreBuilder;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import apporio.com.vehicleapp.NavigationActivities.AboutActivity;
import apporio.com.vehicleapp.NavigationActivities.MyRidesActivity;
import apporio.com.vehicleapp.NavigationActivities.ProfileActivity;
import apporio.com.vehicleapp.NavigationActivities.RideEstimateResultCostActivity;
import apporio.com.vehicleapp.Parsing_Files.Parsing_Drivers_Home;
import apporio.com.vehicleapp.Parsing_Files.Parsing_Rate_Card;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_Add_Device_Id;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_ViewCars;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_callSupport;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;
import apporio.com.vehicleapp.StartUpScreens.SplashActivity;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private Toolbar toolbar;

    public static Typeface font;
    public static Context ctc;
    private LocationRequest mLocationRequest;
    boolean mUpdatesRequested = false;
    private GoogleApiClient mGoogleApiClient;
    public static RecyclerView mRecyclerView;
    public static RecyclerView.LayoutManager mLayoutManager;
    public static DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    private static final int TYPE_ITEM = 1;
    public static RecyclerView.Adapter mAdapter;
    public static String TITLES[] = {"My rides", "About", "Call Support", "Report Issue",  "Logout"};

    public static int ICONS[] = {R.drawable.my_rides,
            R.drawable.about, R.drawable.call_support, R.drawable.report_issue,  R.drawable.logout};


    private int mIcons[];
    private String name;        //String Resource for header View Name
    private int profile;        //int Resource for header view profile picture
    private String email;       //String Resource for header view email
    public static Context ctc2;
    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    public static String NAME;
    public static String EMAIL;
    public static int PROFILE = R.drawable.defaultprof;
    LayoutInflater inflator;
    View v;

    public static GoogleMap mGoogleMap;
    private GPStracker gps;
    double latitude;
    double longitude;

    public static String usercurrentlat;
    public static String usercurrentlong;

    public LatLng curentpoint;
    public LatLng center;
    private LinearLayout markerLayout;
    public static TextView Address;
    private Geocoder geocoder;
    private List<Address> addresses;

    public static MainActivity mainActivity;

    TextView carType, carTime;
    public LinearLayout HlvAddLayout, HlvItemLayout,custommarkerLayout;


    ArrayList<Integer> BcarImages = new ArrayList<Integer>();
    ArrayList<String> carNames = new ArrayList<String>();
    ArrayList<String> carTimes = new ArrayList<String>();
    ArrayList<Integer> ctemp = new ArrayList<Integer>();
    public static LinearLayout llforcnfrm, llforride, llRatecard, rideest;
    public static TextView text, textforschedule, cancel, confirm, text22, cabtype, couponsavailable;
    public static int test = 0;
    TextView ridenow;


    private int myear, posihori;

    public static String currenttime1;
    public static String fulldate;
    MyDatastore datastore;

    private int oldclickvalue = -1;
    private int counter = 0;
    public static String carid,selectedcarname;
    FrameLayout Backofcars;


    PendingResult<LocationSettingsResult> result;
    final static int REQUEST_LOCATION = 199;

    public static TextView carname, carcapacity, minfare, pickfare, milfare, minutefare, nopickup;
    public static ImageView carimageOndialog;
    MapView mapView;

    public static TextView updateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkGps();
        mainActivity = MainActivity.this;
        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(mainActivity))
                .create(MyDatastore.class);
        FirebaseMessaging.getInstance().subscribeToTopic("test");

        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null) {
            Log.e("notification", token);
            Parsing_for_Add_Device_Id.Add_Device_ID(MainActivity.this, datastore.USER_ID().get(), token, "2");
        }


        ctc = getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu1);

        carid=Parsing_for_ViewCars.carId.get(0);



        NAME = datastore.USER_NAME().get();
        EMAIL = datastore.USER_EMAIL().get();


//        if (datastore.DEVICE_ID().get().equals(SplashActivity.regId)){
//          //  Toast.makeText(MainActivity.this, "Already Registered", Toast.LENGTH_SHORT).show();
//        }else {

      //  }


        updateTime=(TextView)findViewById(R.id.minuteincustomview);

        HlvAddLayout = (LinearLayout) findViewById(R.id.hlvSimpleList);
        markerLayout = (LinearLayout) findViewById(R.id.locationMarker);
        Address = (TextView) findViewById(R.id.adressText);
       // llforcnfrm = (LinearLayout) findViewById(R.id.layoutforconfirm);
        llforride = (LinearLayout) findViewById(R.id.layoutforride);
       // cancel = (TextView) findViewById(R.id.cncl);
        ridenow = (TextView) findViewById(R.id.rn);
       // llforcnfrm.setVisibility(View.GONE);
        inflator = LayoutInflater.from(this);
        v = inflator.inflate(R.layout.titleview, null);
       // llRatecard = (LinearLayout) findViewById(R.id.llforratecard);
       // rideest = (LinearLayout) findViewById(R.id.llforrideest);
        nopickup = (TextView) findViewById(R.id.nopickups);

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);


        ((TextView) v.findViewById(R.id.title)).setText("Vehicle App");
        getSupportActionBar().setCustomView(v);


       // confirm = (TextView) findViewById(R.id.cnfrm);

        final Calendar c = Calendar.getInstance();
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String sec = String.valueOf(c.get(Calendar.MINUTE));
        currenttime1 = hour + ":" + sec;
        final String currentdate123 = String.valueOf(c.get(Calendar.DATE));
        final int currentmonth123 = c.get(Calendar.MONTH) + 1;
        //  final String currentmonth123=String.valueOf(c.get(Calendar.MONTH));
        final String currentyear123 = String.valueOf(c.get(Calendar.YEAR));
        Log.e("timmmmmm", "" + currenttime1);
        fulldate = currentdate123 + "/" + currentmonth123 + "/" + currentyear123;
        Log.e("current date", "" + fulldate);

    // Parsing_Rate_Card.ParseRateCard(MainActivity.this, carid);


        ctemp.add(0, 0);

        for (int j = 1; j < Parsing_for_ViewCars.carId.size(); j++) {
            ctemp.add(j, 1);
        }


        createhori();


        font = Typeface.createFromAsset(ctc.getAssets(), "font/Quicksand_Book.otf");

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are
            // not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
                    requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment
            // Create a new global location parameters object
            mLocationRequest = LocationRequest.create();

            /*
             * Set the update interval
             */
            mLocationRequest.setInterval(GData.UPDATE_INTERVAL_IN_MILLISECONDS);

            // Use high accuracy
            mLocationRequest
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            // Set the interval ceiling to one minute
            mLocationRequest
                    .setFastestInterval(GData.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

            // Note that location updates are off until the user turns them on
            mUpdatesRequested = false;

            /*
             * Create a new location client, using the enclosing class to handle
             * callbacks.
             */
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();

            mGoogleApiClient.connect();
        }


//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                llforride.setVisibility(View.VISIBLE);
//                llforcnfrm.setVisibility(View.INVISIBLE);
//                test = 0;
//
//            }
//        });

//        llRatecard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Parsing_Rate_Card.ParseRateCard(MainActivity.this,carid);
//                showratedialog();
//                // startActivity(new Intent(MainActivity.this,RateCardActivity.class));
//            }
//        });


        ridenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test = 0;
                //  Toast.makeText(getApplicationContext(), "Ride now", Toast.LENGTH_SHORT).show();

                //  cartypetext.setText(Parsing_for_ViewCars.cartypename);

        //        textforschedule.setText(currentdate123 + "/" + currentmonth123 + "/" + currentyear123 + " at " + currenttime1);
//
//                llforride.setVisibility(View.INVISIBLE);
//                llforcnfrm.setVisibility(View.VISIBLE);
//                cabtype.setVisibility(View.VISIBLE);
//                cabtype.setText(Parsing_for_ViewCars.carName.get(posihori));
                //   textforschedule.setText(mins[positionforhori]);

                startActivity(new Intent(MainActivity.this, ShipmentActivity.class));

            }
        });


//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, PickImageActivity.class));
//            }
//        });

//        rideest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, RideEstimateResultCostActivity.class));
//            }
//        });


        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(MainActivity.this, TITLES, ICONS, NAME, EMAIL, PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        };

//        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
//        mDrawerToggle.syncState();

    }

    private void createhori() {

        for (int i = 0; i < Parsing_for_ViewCars.carId.size(); i++) {
            HlvAddLayout.addView(HorizontalList(R.layout.layoutforhori, Parsing_for_ViewCars.carImage.get(i), Parsing_for_ViewCars.carName.get(i), Parsing_for_ViewCars.carId.get(i), i, ctemp.get(i)));
        }


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = MainActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(MainActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = MainActivity.this.getWindow();
            //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onResume() {
      stupMap();
      // Address.setText(Helper.Address);

        super.onResume();
    }

    @Override
    protected void onRestart() {
        stupMap();

        super.onRestart();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    public void onLowMemory() {


        super.onLowMemory();
    }

    public void stupMap() {
        try {


            mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

           //  Enabling MyLocation in Google Map

                        mGoogleMap.setMyLocationEnabled(true);
                        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
                        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                        mGoogleMap.getUiSettings().setCompassEnabled(true);
                        mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
                        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);


                        PendingResult<Status> result = LocationServices.FusedLocationApi
                                .requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                                        new com.google.android.gms.location.LocationListener() {

                                            @Override
                                            public void onLocationChanged(Location location) {
                                                //  markerText.setText("Location received: "
                                                // + location.toString());

                                            }
                                        });



            Log.e("Reached", "here");

            result.setResultCallback(new ResultCallback<Status>() {

                @Override
                public void onResult(Status status) {

                    if (status.isSuccess()) {

                        Log.e("Result", "success");

                    } else if (status.hasResolution()) {
                        // Google provides a way to fix the issue
                        try {
                            status.startResolutionForResult(MainActivity.this,
                                    100);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            gps = new GPStracker(this);

            gps.canGetLocation();



//            Helper.Address_Lat=usercurrentlat;
//            Helper.Address_Lng=usercurrentlong;



            if (Helper.count.equals("")){

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                usercurrentlat = Double.toString(latitude);
                usercurrentlong = Double.toString(longitude);


                curentpoint = new LatLng(latitude, longitude);
            }else {
               // double l= Double.parseDouble(Helper.Address_Lat);
                curentpoint=new LatLng(Double.parseDouble(Helper.Address_Lat),Double.parseDouble(Helper.Address_Lng));
                usercurrentlat = Helper.Address_Lat;
                usercurrentlong = Helper.Address_Lng;
                Parsing_Drivers_Home.ParseDrivers(mainActivity, usercurrentlong, usercurrentlong, Parsing_for_ViewCars.carId.get(0));
            }


            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(curentpoint).zoom(15).build();

            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

            mGoogleMap.clear();


            Log.e("lng " + usercurrentlong, "lat " + usercurrentlat);



            // Clears all the existing markers


            mGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                @Override
                public void onCameraChange(CameraPosition arg0) {
                    // TODO Auto-generated method stub
                    center = mGoogleMap.getCameraPosition().target;

                    // markerText.setText(" Set your Location ");
                    mGoogleMap.clear();
                    markerLayout.setVisibility(View.VISIBLE);

                    try {

                        new GetLocationAsync(center.latitude, center.longitude)
                                .execute();


//                        for (int k = 0; k <Parsing_for_homescreen.did.size(); k++) {
//                            //Bitmap bmp = tc.makeIcon(""+ name.get(k));
//
//                            mGoogleMap.addMarker(new MarkerOptions()
//                                    .position(new LatLng(Double.parseDouble((Parsing_for_homescreen.dlat.get(k)).toString()), Double.parseDouble((Parsing_for_homescreen.dlong.get(k)).toString())))
//                                    .title("Cab" + Parsing_for_homescreen.did.get(k)).icon(BitmapDescriptorFactory.fromResource(R.drawable.markeryellow)));
//                            mGoogleMap.setMyLocationEnabled(true);
//
//                        }
                    } catch (Exception e) {
                    }
                }
            });
            mGoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {

                    Parsing_Drivers_Home.ParseDrivers(mainActivity, usercurrentlat, usercurrentlong, carid);
                }
            });

            markerLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(MainActivity.this,Change_AddressActivity.class));


//                    try {
//
//                        LatLng latLng1 = new LatLng(center.latitude,
//                                center.longitude);
//
//                        Marker m = mGoogleMap.addMarker(new MarkerOptions()
//                                .position(latLng1)
//                                .title(" Set your Location ")
//                                .snippet("")
//                                .icon(BitmapDescriptorFactory
//                                        .fromResource(R.drawable.location_marker)));
//                        m.setDraggable(true);
//
//                        markerLayout.setVisibility(View.GONE);
//                    } catch (Exception e) {
//                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        stupMap();

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    private class GetLocationAsync extends AsyncTask<String, Void, String> {

        // boolean duplicateResponse;
        double x, y;
        StringBuilder str;

        public GetLocationAsync(double latitude, double longitude) {
            // TODO Auto-generated constructor stub

            x = latitude;
            y = longitude;
        }

        @Override
        protected void onPreExecute() {
            Address.setText("Getting location");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                geocoder = new Geocoder(MainActivity.this, Locale.ENGLISH);
                addresses = geocoder.getFromLocation(x, y, 1);
                str = new StringBuilder();
                if (geocoder.isPresent()) {
                    Address returnAddress = addresses.get(0);

                    String localityString = returnAddress.getLocality();
                    String city = returnAddress.getCountryName();
                    String region_code = returnAddress.getCountryCode();
                    String zipcode = returnAddress.getPostalCode();

                    str.append(localityString + "");
                    str.append(city + "" + region_code + "");
                    str.append(zipcode + "");


                } else {
                }
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            try {

                String ad=addresses.get(0).getAddressLine(0) + ", "
                        + addresses.get(0).getAddressLine(1) + " ";

                Address.setText(ad);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }




    @Override
    public void onBackPressed() {

        SplashActivity.contexty.finish();

        super.onBackPressed();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        // Creating a ViewHolder which extends the RecyclerView View Holder
        // ViewHolder are used to to store the inflated apporio.com.vehicleapp.views in order to recycle them

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            int Holderid;

            TextView textView;
            ImageView imageView;
            public ImageView profile11;
            TextView Name;
            TextView email;
            LinearLayout itemll, llforprof;

            public ViewHolder(View itemView, int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
                super(itemView);


                // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

                if (ViewType == TYPE_ITEM) {
                    textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
                    imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                    itemll = (LinearLayout) itemView.findViewById(R.id.llfornavi);
                    itemll.setOnClickListener(this);
                    // Creating ImageView object with the id of ImageView from item_row.xml
                    Holderid = 1;

                    // setting holder id as 1 as the object being populated are of type item row
                } else {
                    Typeface font = Typeface.createFromAsset(ctc.getAssets(), "font/Quicksand_Book.otf");

                    Name = (TextView) itemView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
                    email = (TextView) itemView.findViewById(R.id.email);       // Creating Text View object from header.xml for email
                  //  Name.setTypeface(font);
                  //  email.setTypeface(font);
                    profile11 = (ImageView) itemView.findViewById(R.id.profilecircleimage);
                    llforprof = (LinearLayout) itemView.findViewById(R.id.headerprofile);
//                    profile11.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //MainActivity.Drawer.closeDrawer(Gravity.LEFT);
//                            showcamerdialog();
//                        }
//                    });
                    llforprof.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.Drawer.closeDrawer(Gravity.LEFT);
                            Intent in = new Intent(ctc2, ProfileActivity.class);
                            ctc2.startActivity(in);

                        }
                    });

                    // Creating Image view object from header.xml for profile pic
                    Holderid = 0;
                }
            }


            @Override
            public void onClick(View v) {
                if (TITLES[getPosition() - 1].equals("My rides")) {
                    MainActivity.Drawer.closeDrawer(Gravity.LEFT);
                    Intent in = new Intent(ctc2, MyRidesActivity.class);
                    ctc2.startActivity(in);

                } else if (TITLES[getPosition() - 1].equals("About")) {
                    MainActivity.Drawer.closeDrawer(Gravity.LEFT);
                    Intent in = new Intent(ctc2, AboutActivity.class);
                    ctc2.startActivity(in);
                } else if (TITLES[getPosition() - 1].equals("Call Support")) {
                    MainActivity.Drawer.closeDrawer(Gravity.LEFT);
                    String posted_by = Parsing_for_callSupport.callno;
                    String uri = "tel:" + posted_by.trim();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));

                    startActivity(intent);


                } else if (TITLES[getPosition() - 1].equals("Report Issue")) {
                    MainActivity.Drawer.closeDrawer(Gravity.LEFT);
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", "abc@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report Issue Regarding Taxi App");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                    emailIntent.setType("text/plain");

                }

//                } else if (TITLES[getPosition() - 1].equals("Help")){
//                    MainActivity.Drawer.closeDrawer(Gravity.LEFT);
//                    Intent in = new Intent(ctc2, BillActivity.class);
//                    ctc2.startActivity(in);
//                }
                else if(TITLES[getPosition() - 1].equals("Logout")){
                    MainActivity.Drawer.closeDrawer(Gravity.LEFT);
                    Logoutdialog();
                }

            }
        }

        MyAdapter(MainActivity mainActivity, String Titles[], int Icons[], String Name, String Email, int Profile){ // MyAdapter Constructor with titles and icons parameter
            // titles, icons, name, email, profile pic are passed from the main activity as we
            TITLES = Titles;                //have seen earlier
            mIcons = Icons;
            name = Name;
            email = Email;
            profile = Profile;
            ctc2= mainActivity;
            //here we assign those passed values to the values we declared here
            //in adapter


        }

        //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
        //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
        // if the viewType is TYPE_HEADER
        // and pass it to the view holder

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false); //Inflating the layout

                ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

                return vhItem; // Returning the created object

                //inflate your layout and pass it to view holder

            } else if (viewType == TYPE_HEADER) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false); //Inflating the layout

                ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

                return vhHeader; //returning the object created


            }
            return null;

        }

        //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
        // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
        // which view type is being created 1 for item row
        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
            if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
                // position by 1 and pass it to the holder while setting the text and image
                holder.textView.setText(TITLES[position - 1]); // Setting the Text with the array of our Titles
                holder.imageView.setImageResource(mIcons[position - 1]);// Settimg the image with array of our icons
            }
            else{

                //holder.profile11.setImageBitmap(SplashActivity.fbimage);           // Similarly we set the resources for header view
                holder.Name.setText(name);

//                if (datastore.USER_IMAGE().get().equals("0")){
//                    Log.e("imgggg", "" + datastore.USER_IMAGE().get());
//                    holder.profile11.setImageResource(R.drawable.defaultprof);
//                }else {
//                   String s= datastore.USER_IMAGE().get();
//
//                    Log.e("imgggg", "" + s);
//
//                   String[] parts= s.split("\\?");
//                   String url=parts[0];
//                   String url1=parts[1];
//
//                    Picasso.with(ctc).load(url).fit().into(holder.profile11);
//                }
                holder.email.setText(email);

            }

        }

        // This method returns the number of items present in the list
        @Override
        public int getItemCount() {
            return TITLES.length+1; // the number of items in the list will be +1 the titles including the header view.
        }


        // Witht the following method we check what type of view is being passed
        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return TYPE_HEADER;

            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }

    }

    public  View HorizontalList(int layout_name, String carwhiteimage,String carname,String cartime,

                                final int pos,int c) {

        LayoutInflater layoutInflater =
                (LayoutInflater)MainActivity.mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View addView = layoutInflater.inflate(layout_name, null);
       final ImageView carImage,blackCarImage;
        carType=(TextView)addView.findViewById(R.id.category);
      //  carTime=(TextView)addView.findViewById(R.id.mins);
        carImage=(ImageView)addView.findViewById(R.id.whitecar);
       // blackCarImage=(ImageView)addView.findViewById(R.id.blackcar);
        HlvItemLayout=(LinearLayout)addView.findViewById(R.id.layoutclick);
        Backofcars=(FrameLayout)addView.findViewById(R.id.layoutbackforcars);

        carType.setText(carname);
      //  carTime.setText(cartime);

        Picasso.with(ctc).load("http://apporio.in/load_up_app/" + carwhiteimage).fit().into(carImage);
       // carImage.setImageResource(carwhiteimage);
       // blackCarImage.setImageResource(blackcaimage);
       // Backofcars.setBackground(getResources().getDrawable(R.drawable.layout_cars));

        HlvItemLayout.setTag(pos);

        if (c==0){
            //blackCarImage.setVisibility(View.VISIBLE);
            Backofcars.setBackground(getResources().getDrawable(R.drawable.shadow2));
            carImage.setVisibility(View.VISIBLE);
        }else {
          //   blackCarImage.setVisibility(View.GONE);
          //  Backofcars.setBackground(getResources().getDrawable(R.drawable.layout_white_cars));
            carImage.setVisibility(View.VISIBLE);
        }


       HlvItemLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               posihori= (int) v.getTag();
               carid=Parsing_for_ViewCars.carId.get(posihori);
               selectedcarname=Parsing_for_ViewCars.carName.get(posihori);

               Parsing_Rate_Card.ParseRateCard(MainActivity.this,carid);


                 Log.e("selected car id",""+carid);
               for (int i = 0; i <Parsing_for_ViewCars.carId.size(); i++) {
                   ctemp.add(i,1);
                }

               Log.e("position cars",""+posihori);

              ctemp.add(posihori, 0);
             HlvAddLayout.removeAllViews();
               createhori();

////////////////////////////////////////////////////////////

               if(posihori != oldclickvalue){
                   counter  = 1  ;
                   oldclickvalue = posihori ;
               }else {
                   counter  = 2 ;
               }

               counterdeseptor();

           }
       });

        return addView ;
    }



    public void counterdeseptor (){

        if(counter == 1 ){
            parsing();
        }else  if (counter  == 2 ){
            showratedialog();
        }

    }



    public void showratedialog(){

        final Dialog dialog = new Dialog(MainActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=dialog.getWindow();
        dialog.setCancelable(true);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogue_rate_card);

        carname=(TextView)dialog.findViewById(R.id.Dcarname);
        carcapacity=(TextView)dialog.findViewById(R.id.Dcapacity);
        minfare=(TextView)dialog.findViewById(R.id.Dmin);
        pickfare=(TextView)dialog.findViewById(R.id.Dpick);
        milfare=(TextView)dialog.findViewById(R.id.Dmile);
        minutefare=(TextView)dialog.findViewById(R.id.Dminute);
        carimageOndialog=(ImageView)dialog.findViewById(R.id.Dcarimage);


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        dialog.show();

    }

    public void parsing(){

        Parsing_Drivers_Home.ParseDrivers(mainActivity, usercurrentlat, usercurrentlong, carid);

     }

   public void Logoutdialog(){

       final Dialog dialog = new Dialog(MainActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       Window window=dialog.getWindow();
       dialog.setCancelable(false);
       window.setGravity(Gravity.CENTER);
       window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       dialog.setContentView(R.layout.dialogforlogout);
       TextView Title=(TextView)dialog.findViewById(R.id.headingLogout);
       Title.setText("Logout");
       TextView msg=(TextView)dialog.findViewById(R.id.msggRide);
       msg.setText("Are You sure want to logout ?");
       TextView yes = (TextView)dialog.findViewById(R.id.yes);
       TextView no = (TextView)dialog.findViewById(R.id.no);

       yes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
               SharedPreferences.Editor edit2 = prefs.edit();
               edit2.putBoolean("pref_previously_started", false);
               edit2.commit();

               Helper.Address = "";
               Helper.Place_id = "";
               Helper.Address_Lat = "";
               Helper.Address_Lng = "";
               Helper.checkPay = "";
               Helper.count = "";
               Helper.DestinationAddress_Lat = "";
               Helper.DestinationAddress_Lng = "";
               Helper.ShipImages.clear();
               Helper.ShipWeight.clear();
               Helper.ShipInstrunction.clear();
               Helper.ShipQunatity.clear();
               Helper.ShipImagePath.clear();

               Helper.  Rname="";
               Helper.  Raddress="";
               Helper.  Rphone="";
               Helper.  Remail="";


               Intent in = new Intent(MainActivity.this, SplashActivity.class);
               startActivity(in);
               finish();

              // SplashActivity.contexty.finish();

               dialog.dismiss();
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

    public void checkGps(){
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //   showcheckGPSdialog();
            //     Ask the user to enable GPS
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS settings");
            builder.setMessage("GPS is not enabled. Do you want to go to settings menu ?");
            builder.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Launch settings, allowing user to make a change
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                    stupMap();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //No location service, no Activity
                    finish();
                }
            });
            builder.create().show();
        }

        else {
            // startActivity(new Intent(SplashActivity.this,MainActivity.class));
        }


    }




}
