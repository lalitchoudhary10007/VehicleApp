package apporio.com.vehicleapp.NavigationActivities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import apporio.com.vehicleapp.Parsing_Files.Parsing_for_AboutUs;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_termsandconditions;
import apporio.com.vehicleapp.R;

public class AboutActivity extends Activity {


    LinearLayout backLayout,tandc;
    public static TextView aboutText,tnc2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        backLayout=(LinearLayout)findViewById(R.id.aboutback);
        aboutText=(TextView)findViewById(R.id.abouustextv);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tandc=(LinearLayout)findViewById(R.id.tandclayout);

        tandc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showtncdialog();
            }
        });

        Parsing_for_AboutUs.ParseAboutus(AboutActivity.this);

        Parsing_for_termsandconditions.termsand_cnditions_Parse(AboutActivity.this);

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = AboutActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(AboutActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = AboutActivity.this.getWindow();
            //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public  void showtncdialog() {

        final Dialog dialog = new Dialog(AboutActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=dialog.getWindow();
        dialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogue_t_and_c);
        tnc2 = (TextView)dialog.findViewById(R.id.tex);
        TextView done=(TextView)dialog.findViewById(R.id.donedi);
        tnc2.setText(Parsing_for_termsandconditions.data);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}
