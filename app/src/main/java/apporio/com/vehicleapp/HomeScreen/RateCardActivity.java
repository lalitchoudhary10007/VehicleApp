package apporio.com.vehicleapp.HomeScreen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import apporio.com.vehicleapp.R;

public class RateCardActivity extends Activity {

     String s[]={"City 1","City 2","City 3","City 4","City 5"};
    String titles11[] = {"PICKUP","PICKUP","PICKUP","PICKUP","PICKUP"};
    Spinner spcity,spcar;
    LinearLayout backLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_card);

        spcity=(Spinner)findViewById(R.id.spinnercity);
        spcar=(Spinner)findViewById(R.id.spinnercar);
        backLayout=(LinearLayout)findViewById(R.id.backinratecard);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayAdapter adp223 = new ArrayAdapter(getApplicationContext(),R.layout.itemlayoutforspinner,R.id.txtname,s);
        spcity.setAdapter(adp223);

        ArrayAdapter adp22 = new ArrayAdapter(getApplicationContext(),R.layout.itemlayoutforspinner,R.id.txtname, titles11);
        spcar.setAdapter(adp22);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = RateCardActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(RateCardActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = RateCardActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
