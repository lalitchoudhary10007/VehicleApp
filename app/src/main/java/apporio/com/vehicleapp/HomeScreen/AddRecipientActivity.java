package apporio.com.vehicleapp.HomeScreen;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import apporio.com.vehicleapp.GooglePlaces.parsingforoffers;
import apporio.com.vehicleapp.Parsing_Files.Parsing_For_LatLong;
import apporio.com.vehicleapp.R;

public class AddRecipientActivity extends Activity {

    EditText name,phone,email;
    TextView rdone;
    public static AutoCompleteTextView atvPlaces_Shipment;

    static String selectedtext="null";
    String selectedaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipient);

        name=(EditText)findViewById(R.id.Rname);
        phone=(EditText)findViewById(R.id.Rphone);
        email=(EditText)findViewById(R.id.Remail);
        //address=(EditText)findViewById(R.id.Raddress);
        rdone=(TextView)findViewById(R.id.Addrecepinetdone);
        atvPlaces_Shipment=(AutoCompleteTextView)findViewById(R.id.atv_places_in_shipment);
        atvPlaces_Shipment.setThreshold(1);


        atvPlaces_Shipment.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                parsingforoffers.parsing(AddRecipientActivity.this, atvPlaces_Shipment.getText().toString().trim());

                ArrayAdapter adapter = new ArrayAdapter(AddRecipientActivity.this, android.R.layout.simple_list_item_1, parsingforoffers.descp);
                atvPlaces_Shipment.setAdapter(adapter);

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

        atvPlaces_Shipment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SELECTED TEXT WAS----->", (String) parent.getItemAtPosition(position));
                selectedtext = parsingforoffers.postid.get(position);
                selectedaddress = (String) parent.getItemAtPosition(position);
                Helper.Raddress = selectedtext ;
                Helper.Place_id = selectedtext ;
                Log.e("yugiugig", "" + selectedtext);
                Log.e("address position",""+selectedaddress);
                Helper.checkPay = "2";

                Parsing_For_LatLong.parsing(AddRecipientActivity.this, selectedtext);

            }
        });

        rdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().equals("")&&phone.getText().toString().equals("")&&atvPlaces_Shipment.getText().toString().equals("")&&email.getText().toString().equals("")){
                    Toast.makeText(AddRecipientActivity.this, "Plz Fill All Details !!", Toast.LENGTH_SHORT).show();
                }else {
                    Helper.Rname=name.getText().toString();
                    Helper.Raddress=atvPlaces_Shipment.getText().toString();
                    Helper.Remail=email.getText().toString();
                    Helper.Rphone=phone.getText().toString();

                  Log.e("plce id send",""+Helper.Place_id);
                    finish();


                }


            }
        });



    }
}
