package apporio.com.vehicleapp.StartUpScreens;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import apporio.com.vehicleapp.Parsing_Files.Parsing_for_Forgotpassword;
import apporio.com.vehicleapp.R;

public class ForgotPassword extends Activity {

    LinearLayout backLayout;
    TextView forgotView;
    EditText editmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backLayout=(LinearLayout)findViewById(R.id.fpsback);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        forgotView=(TextView)findViewById(R.id.forgotdone);
        editmail=(EditText)findViewById(R.id.forgotemail);


        forgotView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editmail.getText().toString().trim().equals("")){
                    Toast.makeText(ForgotPassword.this, "Please enter Email-Id !!!", Toast.LENGTH_SHORT).show();
                }else {
                    Parsing_for_Forgotpassword.forgotpasswordparsing(ForgotPassword.this,editmail.getText().toString().trim());
                }


            }
        });


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = ForgotPassword.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ForgotPassword.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = ForgotPassword.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
