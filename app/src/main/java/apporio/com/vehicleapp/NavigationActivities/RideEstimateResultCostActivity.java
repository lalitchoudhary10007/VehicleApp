package apporio.com.vehicleapp.NavigationActivities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import apporio.com.vehicleapp.GooglePlaces.parsingforoffers;
import apporio.com.vehicleapp.HomeScreen.MainActivity;
import apporio.com.vehicleapp.HomeScreen.PickImageActivity;
import apporio.com.vehicleapp.HomeScreen.ShipmentActivity;
import apporio.com.vehicleapp.Parsing_Files.Parsing_For_LatLong;
import apporio.com.vehicleapp.Parsing_Files.Parsing_For_RideEstimate;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_view_Helper_price;
import apporio.com.vehicleapp.R;

public class RideEstimateResultCostActivity extends Activity {

    TextView ok,check;
    LinearLayout drop;
   // ImageView ratefare,close;
    public static TextView Tpay,Tdistane;
    TextView firstrate,afterrate,waitrate,pickupaddress,dropaddress;
    public static String first,after,waitcharg,totalkm,totalpay;
    public static AutoCompleteTextView atvPlaces;
    public static String selectedtext="null";
    public static LinearLayout RideEstShow,backLinearLayout;
    public static String selectedaddress;

    TextView Helper_Price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_estimate_result_cost);

        ok = (TextView)findViewById(R.id.ok);
        check = (TextView)findViewById(R.id.checkbtn);
        drop = (LinearLayout)findViewById(R.id.llfordrop);

//        pay1=(TextView)findViewById(R.id.payy1);
//        pay2=(TextView)findViewById(R.id.payy2);
        Tdistane=(TextView)findViewById(R.id.totldistance);
        pickupaddress=(TextView)findViewById(R.id.pickup);
        Helper_Price=(TextView)findViewById(R.id.helper_price);
        RideEstShow=(LinearLayout)findViewById(R.id.rideestimateshow);
        backLinearLayout=(LinearLayout)findViewById(R.id.rideestimateback);
        RideEstShow.setVisibility(View.GONE);
        Tpay=(TextView)findViewById(R.id.charges);

        atvPlaces = (AutoCompleteTextView) findViewById(R.id.atv_places);


        pickupaddress.setText(MainActivity.Address.getText());

        backLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        try {


        Intent ii= getIntent();
       String add =  ii.getStringExtra("address");
        atvPlaces.setText(add);
        Parsing_For_LatLong.parsing(RideEstimateResultCostActivity.this, add);
        }catch (Exception e){
            Log.e("exception",""+e);
        }
        atvPlaces.setThreshold(1);

        atvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SELECTED TEXT WAS----->", (String) parent.getItemAtPosition(position));
                selectedtext = parsingforoffers.postid.get(position);
                selectedaddress = (String) parent.getItemAtPosition(position);
                Log.e("", "" + selectedtext);

               Parsing_For_LatLong.parsing(RideEstimateResultCostActivity.this, selectedtext);

            }
        });

        atvPlaces.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                parsingforoffers.parsing(RideEstimateResultCostActivity.this, atvPlaces.getText().toString().trim());

                ArrayAdapter adapter = new ArrayAdapter(RideEstimateResultCostActivity.this, R.layout.itemlayoutforplaces, R.id.mobcode,parsingforoffers.descp);
                atvPlaces.setAdapter(adapter);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });



     check.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             if (atvPlaces.getText().toString().trim().equals("")){
                 Toast.makeText(RideEstimateResultCostActivity.this, "Please select drop location !!!", Toast.LENGTH_SHORT).show();
             }else {
                 RideEstShow.setVisibility(View.VISIBLE);

                 Parsing_For_RideEstimate.parseRideEstimate(RideEstimateResultCostActivity.this,MainActivity.usercurrentlat,MainActivity.usercurrentlong,String.valueOf(Parsing_For_LatLong.lat),String.valueOf(Parsing_For_LatLong.lng),
                         MainActivity.carid);

                 if (ShipmentActivity.helpervalue.equals("1")){
                     Helper_Price.setVisibility(View.VISIBLE);
                     Helper_Price.setText("Helper Charge :- "+Parsing_for_view_Helper_price.Price_list.get(0));
                 }else {
                     Helper_Price.setVisibility(View.GONE);
                 }


                 InputMethodManager inputMgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                 inputMgr.hideSoftInputFromWindow(check.getWindowToken(), 0);
             }

         }
     });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = RideEstimateResultCostActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(RideEstimateResultCostActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = RideEstimateResultCostActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
