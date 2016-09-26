package apporio.com.vehicleapp.HomeScreen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import apporio.com.vehicleapp.GooglePlaces.parsingforoffers;
import apporio.com.vehicleapp.Parsing_Files.Parsing_For_LatLong;
import apporio.com.vehicleapp.R;

public class Change_AddressActivity extends Activity {

    TextView done;
    public static AutoCompleteTextView atvPlaces2;
    String selectedtext="null";
    String selectedaddress;
    LinearLayout backLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__address);

        atvPlaces2 = (AutoCompleteTextView) findViewById(R.id.atv_places2);
        atvPlaces2.setThreshold(1);
        done=(TextView)findViewById(R.id.doneaddress);
        backLayout=(LinearLayout)findViewById(R.id.changeaddrssback);

        atvPlaces2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SELECTED TEXT WAS----->", (String) parent.getItemAtPosition(position));
                selectedtext = parsingforoffers.postid.get(position);
                selectedaddress = (String) parent.getItemAtPosition(position);
                Log.e("", "" + selectedtext);
                Helper.Address=selectedaddress;

                Helper.checkPay="1";

                Parsing_For_LatLong.parsing(Change_AddressActivity.this, selectedtext);

            }
        });

        atvPlaces2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                parsingforoffers.parsing(Change_AddressActivity.this, atvPlaces2.getText().toString().trim());

                ArrayAdapter adapter = new ArrayAdapter(Change_AddressActivity.this, R.layout.itemlayoutforplaces, R.id.mobcode, parsingforoffers.descp);
                atvPlaces2.setAdapter(adapter);
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

         done.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Helper.count="1";
                startActivity(new Intent(Change_AddressActivity.this,MainActivity.class));
             }
         });

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
