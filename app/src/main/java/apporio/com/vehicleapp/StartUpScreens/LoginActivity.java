package apporio.com.vehicleapp.StartUpScreens;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import apporio.com.vehicleapp.Checkers.EmailChecker;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_Login;
import apporio.com.vehicleapp.R;

public class LoginActivity extends Activity {

    TextView create_Ac,Login,fps;
    EditText email,password;
    View Vemail1,Vemail2,Vpass1,Vpass2;
    public static LoginActivity loginactivity;
    public static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginactivity=LoginActivity.this;

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        Vemail1=(View)findViewById(R.id.viewemail1);
        Vemail2=(View)findViewById(R.id.viewemail2);
        Vpass1=(View)findViewById(R.id.viewpass1);
        Vpass2=(View)findViewById(R.id.viewpass2);
        create_Ac=(TextView)findViewById(R.id.createaccnt);

        fps=(TextView)findViewById(R.id.forgotpass);

        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Login=(TextView)findViewById(R.id.loginview);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    Vemail1.setVisibility(View.GONE);
                    Vemail2.setVisibility(View.VISIBLE);
                }
                else{
                    Vemail1.setVisibility(View.VISIBLE);
                    Vemail2.setVisibility(View.GONE);
                }
             }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    Vpass1.setVisibility(View.GONE);
                    Vpass2.setVisibility(View.VISIBLE);
                }
                else{
                    Vpass1.setVisibility(View.VISIBLE);
                    Vpass2.setVisibility(View.GONE);
                }

            }
        });

      create_Ac.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
          }
      });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail=email.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if (mail.equals("")||pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please Enter Details", Toast.LENGTH_LONG).show();
                } else if (!email.equals("")) {
                    if (new EmailChecker().isEmailIsCorrect(mail)) {
                        try {
                            Parsing_for_Login.LoginParse(loginactivity, mail, pass, "2");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Please Enter your Registered email", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });


        fps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = LoginActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(LoginActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = LoginActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }



}
