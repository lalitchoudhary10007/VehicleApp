package apporio.com.vehicleapp.NavigationActivities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lacronicus.easydatastorelib.DatastoreBuilder;

import apporio.com.vehicleapp.Fragments.CurrentOrderFragment;
import apporio.com.vehicleapp.Fragments.PastOrderFragment;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_ViewMyrides;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;
import apporio.com.vehicleapp.views.ProgressWheel;

public class MyRidesActivity extends FragmentActivity {


    LinearLayout backLayout;

    public static TextView newrides,pastrides;

    FragmentTransaction ft ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides);

        backLayout=(LinearLayout)findViewById(R.id.myRidesback);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newrides=(TextView)findViewById(R.id.NewOrder);
        pastrides=(TextView)findViewById(R.id.PastOrder);
        pastrides.setBackground(getResources().getDrawable(R.drawable.black_stroke));
        newrides.setBackground(getResources().getDrawable(R.drawable.blackbg));
        newrides.setTextColor(getResources().getColor(R.color.white));
        pastrides.setTextColor(getResources().getColor(R.color.color_primary));


        setfragmentinContainer(new CurrentOrderFragment(), "" + "Current", 1);


        newrides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             pastrides.setBackground(getResources().getDrawable(R.drawable.black_stroke));
             newrides.setBackground(getResources().getDrawable(R.drawable.blackbg));
             newrides.setTextColor(getResources().getColor(R.color.white));
             pastrides.setTextColor(getResources().getColor(R.color.color_primary));

                setfragmentinContainer(new CurrentOrderFragment(), "" + "Current", 1);


            }
        });

        pastrides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pastrides.setBackground(getResources().getDrawable(R.drawable.blackbg));
                newrides.setBackground(getResources().getDrawable(R.drawable.black_stroke));
                newrides.setTextColor(getResources().getColor(R.color.color_primary));
                pastrides.setTextColor(getResources().getColor(R.color.white));

                setfragmentinContainer(new PastOrderFragment(), "" + "Current", 1);

            }
        });


    }


    private void  setfragmentinContainer(Fragment fragment , String fragment_name , int colour ) {
        if(colour == 1){
        }else if(colour == 2){
        }else if(colour == 3 ){
        }

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container
                , fragment);
        ft.commit();


    }






    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = MyRidesActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(MyRidesActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = MyRidesActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
