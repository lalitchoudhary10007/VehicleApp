package apporio.com.vehicleapp.HomeScreen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lacronicus.easydatastorelib.DatastoreBuilder;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import apporio.com.vehicleapp.Adapters.ShipMentAdapter;
import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.GooglePlaces.parsingforoffers;
import apporio.com.vehicleapp.NavigationActivities.RideEstimateResultCostActivity;
import apporio.com.vehicleapp.Parsing_Files.Parsing_For_LatLong;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_ride_confirm;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_view_Helper_price;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_view_shipment;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.DriverInfoResult;
import apporio.com.vehicleapp.Setter_Getter_Files.PaypalResponse_setter_getter;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;

public class ShipmentActivity extends Activity {

    public static LinearLayout Add_Shipment,ShipListlayout,DropLayout;
    LinearLayout ChangeAddress,Add_Recepient;
    TextView adress_shipment,add_more,Book_confirm,ride_estimate;
   // Spinner Payment_Option;
    String[] options={"Plz Select","CASH","PAYPAL"};
    private String selectedPayOption,note;
    public static ListView ShipListView;
    EditText instrunction;
    TextView Rec_name,Rec_address,Rec_email,Rec_phone,Rec_Edit;
    LinearLayout Rec_layout,backLinearLayout;
    RadioGroup rg;
    public static String helpervalue="";


    public static Dialog RideConfirmDialog,RideEstimateDialog;

    MyDatastore datastore;
    public static ShipmentActivity Shipmentactivity;


    public static String rideidbook;
    public static String driverlat;
    public static String driverlong;
    public static String drivername1;
    public static String driverid;
    public static String driverimage;
    public static String drivercarno1;
    public static String driverphoneno1;
    public static String carname,DriverStar;

    public static String duration;

    String lat,lng;

//    public static LinearLayout paymentLinearLayout;
//    public static TextView total_Price,total_KM;

////////////////////////////////

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_CLIENT_ID = "AFcWxV21C7fd0v3bYYYRCpSSRl31AW.nrY8UUmkTDBx-TSEQlHYBvptc";


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);


    JSONObject RecepientDetails;
    public static JSONArray ShipmentJsonSend;

   public static ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        setContentView(R.layout.activity_shipment);

        Shipmentactivity=ShipmentActivity.this;

        ChangeAddress=(LinearLayout)findViewById(R.id.ChangeAddressImage);
        adress_shipment=(TextView)findViewById(R.id.adressinshipment);
        Add_Shipment=(LinearLayout)findViewById(R.id.AddShipment);
      // Payment_Option=(Spinner)findViewById(R.id.paymentoption);
        ShipListView=(ListView)findViewById(R.id.ShipMentList);
        Book_confirm=(TextView)findViewById(R.id.bookconfirm);
        adress_shipment.setText(MainActivity.Address.getText().toString());
        ShipListlayout=(LinearLayout)findViewById(R.id.shiplistLayout);
        add_more=(TextView)findViewById(R.id.Addmore);
        Add_Recepient=(LinearLayout)findViewById(R.id.AddReceipment);
        instrunction=(EditText)findViewById(R.id.spclinstrnctions);
     //   paymentLinearLayout=(LinearLayout)findViewById(R.id.payment_layout);
      //  total_Price=(TextView)findViewById(R.id.total_rupees);
      //  total_KM=(TextView)findViewById(R.id.total_km);
        DropLayout=(LinearLayout) findViewById(R.id.dropaddresslayout);
        scrollView=(ScrollView)findViewById(R.id.scroll);
        ride_estimate=(TextView)findViewById(R.id.estimate);


        Rec_name=(TextView)findViewById(R.id.rname);
        Rec_address=(TextView)findViewById(R.id.raddress);
        Rec_email=(TextView)findViewById(R.id.remail);
        Rec_phone=(TextView)findViewById(R.id.rphone);
        Rec_layout=(LinearLayout)findViewById(R.id.rlayout);
        Rec_Edit=(TextView) findViewById(R.id.edit_recepient);

        rg = (RadioGroup) findViewById(R.id.radioGroup1);

        backLinearLayout = (LinearLayout)findViewById(R.id.shipmentback);
        backLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(Shipmentactivity))
                .create(MyDatastore.class);

        Parsing_for_view_Helper_price.View_Price(Shipmentactivity);

//        ArrayAdapter ad= new ArrayAdapter(ShipmentActivity.this, android.R.layout.simple_spinner_item,options);
//        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Payment_Option.setAdapter(ad);


                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.yesradioButton:
                        // do operations specific to this selection
                      //  Toast.makeText(PickImageActivity.this, "yess", Toast.LENGTH_SHORT).show();
                        helpervalue="1";

                        break;

                    case R.id.noradiobutton:
                        // do operations specific to this selection
                      //  Toast.makeText(PickImageActivity.this, "nooo", Toast.LENGTH_SHORT).show();
                        helpervalue="0";

                        break;

                }


            }
        });



        ChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShipmentActivity.this,Change_AddressActivity.class));
            }
        });
        Add_Shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShipmentActivity.this,PickImageActivity.class));
            }
        });

//        Payment_Option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedPayOption = parent.getItemAtPosition(position).toString();
//                Log.e("selected pay option",""+selectedPayOption);
//
//                DoPayment();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShipmentActivity.this,PickImageActivity.class));
            }
        });
        Add_Recepient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShipmentActivity.this,AddRecipientActivity.class));
            }
        });
        Rec_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShipmentActivity.this,AddRecipientActivity.class));
            }
        });

        Book_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Shipmentactivity.finish();
//                Intent in = new Intent(Shipmentactivity, WaitingActivity.class);
//                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                Shipmentactivity.startActivity(in);



                if (Helper.Address_Lng.equals("")) {
                    lat = MainActivity.usercurrentlat;
                    lng = MainActivity.usercurrentlong;
                } else {
                    lat = Helper.Address_Lat;
                    lng = Helper.Address_Lng;
                }

                if (Helper.ShipImages.isEmpty()) {
                    Toast.makeText(ShipmentActivity.this, "Plz Enter Shipment Details !!!", Toast.LENGTH_SHORT).show();
                } else if (Rec_name.getText().toString().equals("")) {
                    Toast.makeText(ShipmentActivity.this, "Plz Enter Recepient Details !!!", Toast.LENGTH_SHORT).show();
                } else if (helpervalue.equals("")) {
                    Toast.makeText(ShipmentActivity.this, "Plz Select Helper !!!", Toast.LENGTH_SHORT).show();
                }
                else if (Helper.Raddress.equals("")){
                    Toast.makeText(ShipmentActivity.this, "Plz Enter Drop/Receipent Address !!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Parsing_for_ride_confirm.RideNowConfirm(Shipmentactivity, datastore.USER_ID().get(), lat, lng, Helper.DestinationAddress_Lat, Helper.DestinationAddress_Lng, MainActivity.currenttime1, MainActivity.fulldate
                            , helpervalue, MainActivity.carid, RecepientDetails, ShipmentJsonSend, Helper.Place_id,"PN7");
                }

            }
        });

        ride_estimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helpervalue.equals("")) {
                    Toast.makeText(ShipmentActivity.this, "Plz Select Helper !!!", Toast.LENGTH_SHORT).show();
                } else {
                    ShowRideEstimateDialog();
                }
            }
        });



    }



    public static void ShowRideConfirmDialog(){

        RideConfirmDialog = new Dialog(Shipmentactivity,android.R.style.Theme_DeviceDefault_Light_Dialog);
        RideConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=RideConfirmDialog.getWindow();
        RideConfirmDialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RideConfirmDialog.setContentView(R.layout.dialog_ride_confirmation);

        TextView msg=(TextView)RideConfirmDialog.findViewById(R.id.driverarrivetxt);
        msg.setText("Your ride has been booked Successfully !!!");
        TextView ok=(TextView)RideConfirmDialog.findViewById(R.id.confirmok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent in = new Intent(Shipmentactivity, DriverdetailsActivity.class);
//                in.putExtra("name", drivername1);
//                in.putExtra("carname", carname);
//                in.putExtra("carno", drivercarno1);
//                in.putExtra("driverimage", driverimage);
//                in.putExtra("driverlat", driverlat);
//                in.putExtra("driverlong", driverlong);
//                in.putExtra("duration", duration);
//                in.putExtra("id", driverid);
//                in.putExtra("star", DriverStar);
                RideConfirmDialog.dismiss();
                Shipmentactivity.finish();
                Intent in = new Intent(Shipmentactivity, WaitingActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Shipmentactivity.startActivity(in);
             //   Shipmentactivity.startActivity(in);
            }
        });



        RideConfirmDialog.show();

    }

    public static void ShowRideEstimateDialog(){

        RideEstimateDialog = new Dialog(Shipmentactivity,android.R.style.Theme_DeviceDefault_Light_Dialog);
        RideEstimateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=RideEstimateDialog.getWindow();
        RideEstimateDialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RideEstimateDialog.setContentView(R.layout.dialogforlogout);

        TextView head=(TextView)RideEstimateDialog.findViewById(R.id.headingLogout);
        head.setText("Check Ride Estimate");

        TextView msg=(TextView)RideEstimateDialog.findViewById(R.id.msggRide);
        msg.setText("Helper Charges will be added if you want to hire a helper .");
        TextView yes=(TextView)RideEstimateDialog.findViewById(R.id.yes);
        TextView no=(TextView)RideEstimateDialog.findViewById(R.id.no);
        yes.setText("Cancel");
        no.setText("OK");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RideEstimateDialog.dismiss();

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RideEstimateDialog.dismiss();
                Intent i = new Intent(Shipmentactivity,RideEstimateResultCostActivity.class);
                i.putExtra("address",Helper.Raddress);
                Shipmentactivity.startActivity(i);
            }
        });


        RideEstimateDialog.show();

    }





    @Override
    protected void onResume() {
        super.onResume();

       // Parsing_for_view_shipment.ViewShipment(ShipmentActivity.this);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });


        if (Helper.ShipImages.isEmpty()){
           Add_Shipment.setVisibility(View.VISIBLE);
           ShipListlayout.setVisibility(View.GONE);
        }else {

            Add_Shipment.setVisibility(View.GONE);
            ShipListlayout.setVisibility(View.VISIBLE);

            ShipMentAdapter ad=new ShipMentAdapter(Shipmentactivity, Helper.ShipImages,Helper.ShipWeight,Helper.ShipInstrunction,Helper.ShipQunatity);
            ShipListView.setAdapter(ad);
            setListViewHeightBasedOnChildren(ShipListView);

            ShipmentJsonSend=ShipmentJson();

        }


        if (Helper.Rname.equals("")){
            Add_Recepient.setVisibility(View.VISIBLE);
            Rec_layout.setVisibility(View.GONE);
        }else {
            Add_Recepient.setVisibility(View.GONE);
            Rec_layout.setVisibility(View.VISIBLE);

            Rec_name.setText(Helper.Rname);
            Rec_address.setText(Helper.Raddress);
            Rec_email.setText(Helper.Remail);
            Rec_phone.setText(Helper.Rphone);

            RecepientDetails=ReceipentDetails(Helper.Rname,Helper.Raddress,Helper.Remail,Helper.Rphone);
            Log.e("receipent details", "" + RecepientDetails);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
          //  DropLayout.setVisibility(View.VISIBLE);



        }





    }

    public static JSONObject ReceipentDetails(String name,String address, String mail,String phone) {

        JSONObject jinnerobject = new JSONObject();

        try {

                jinnerobject.put("receipent_name", name);
                jinnerobject.put("receipent_address", address);
                jinnerobject.put("receipent_emailid",mail);
                jinnerobject.put("receipent_phonenumber", phone);


        } catch (JSONException e) {
            e.printStackTrace();
        }



        return jinnerobject;
    }


    public static JSONArray ShipmentJson() {

        JSONArray jsonArray = new JSONArray();

        try {
            for (int i = 0; i < Helper.ShipImages.size(); i++) {
                JSONObject jinnerobject = new JSONObject();

                jinnerobject.put("shipment_weight", Helper.ShipWeight.get(i));
                jinnerobject.put("shipment_image", Helper.ShipImagePath.get(i));
                jinnerobject.put("shipment_quantity",Helper.ShipQunatity.get(i));
                jinnerobject.put("shipment_spcl_instruction", Helper.ShipInstrunction.get(i));

                jsonArray.put(jinnerobject);
            }

            Log.e("JSONARRAY ", jsonArray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }






    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = ShipmentActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ShipmentActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = ShipmentActivity.this.getWindow();
            //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void DoPayment(){

        if (selectedPayOption.equals("CASH")){

        }else if (selectedPayOption.equals("PAYPAL")){

            PayPalPayment payment = new PayPalPayment(new BigDecimal("10"), "USD", "Pay",
                    PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(ShipmentActivity.this, PaymentActivity.class);
            // send the same configuration for restart resiliency
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(intent, 0);

        }else {

        }




    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    String response =confirm.toJSONObject().toString(4);

                    Log.e("pay pal ",""+response);

                    PaypalResponse_setter_getter paypalresponse  = new PaypalResponse_setter_getter();
                    Gson gson  = new Gson();

                    paypalresponse =  gson.fromJson(response, PaypalResponse_setter_getter.class);

                    if(paypalresponse!=null)
                    {

                        Log.e("create time",""+paypalresponse.response.create_time);
                        Log.e("ids",""+paypalresponse.response.ids);
                        Log.e("intents",""+paypalresponse.response.intents);
                        Log.e("state", "" + paypalresponse.response.state);


                    }

                    else
                    {
                        Toast.makeText(Shipmentactivity, "No Responce ", Toast.LENGTH_SHORT).show();
                    }

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("paymentExample", "The user canceled.");
            Toast.makeText(ShipmentActivity.this, "Cancelled ", Toast.LENGTH_SHORT).show();
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

            Toast.makeText(ShipmentActivity.this, "Invalid Payment ", Toast.LENGTH_SHORT).show();
            Log.e("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }



}
