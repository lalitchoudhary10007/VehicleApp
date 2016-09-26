package apporio.com.vehicleapp.NavigationActivities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lacronicus.easydatastorelib.DatastoreBuilder;

import apporio.com.vehicleapp.Parsing_Files.Parsing_for_ChangePassword;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_Editprofile;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;

public class ProfileActivity extends Activity {

    LinearLayout backLayout;
    EditText name,mobile,mail;
    MyDatastore datastore;
    TextView doneTextView,email,password;
    public static ProfileActivity profileActivity;
    LinearLayout llforpassword;
    public static EditText oldp,conp,newp;
    public static Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileActivity=ProfileActivity.this;

        backLayout=(LinearLayout)findViewById(R.id.profileback);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doneTextView=(TextView)findViewById(R.id.profiledone);
        email=(TextView)findViewById(R.id.emailprof);
        password=(TextView)findViewById(R.id.passwrdprof);
        llforpassword=(LinearLayout)findViewById(R.id.ll22);

        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this))
                .create(MyDatastore.class);

        name=(EditText)findViewById(R.id.nameinedit);
        mobile=(EditText)findViewById(R.id.mobileinedit);

        email.setText(datastore.USER_EMAIL().get());
        name.setText(datastore.USER_NAME().get());
        mobile.setText(datastore.USER_PHONE().get());



      doneTextView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Parsing_for_Editprofile.parseEditProfile(profileActivity, datastore.USER_ID().get(), name.getText().toString().trim(), mobile.getText().toString().trim());
          }
      });

        llforpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogforpassword();
            }
        });



    }

    private void showdialogforpassword() {


        dialog = new Dialog(ProfileActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=dialog.getWindow();
        dialog.setCancelable(true);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_change_password);
        TextView done = (TextView)dialog.findViewById(R.id.donedial);
        final TextView alert=(TextView)dialog.findViewById(R.id.passalert);
        oldp=(EditText)dialog.findViewById(R.id.oldp);
         conp=(EditText)dialog.findViewById(R.id.newp);
        newp=(EditText)dialog.findViewById(R.id.conp);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpassword = oldp.getText().toString().trim();
                String confirmpassword = conp.getText().toString().trim();
                String newpassword = newp.getText().toString().trim();

                if (confirmpassword.equals(newpassword)) {
                    alert.setVisibility(View.GONE);
                    Parsing_for_ChangePassword.ParseChangepassword(ProfileActivity.this, datastore.USER_ID().get(), newpassword, oldpassword);

                } else {
                    alert.setVisibility(View.VISIBLE);

                }


            }
        });

        dialog.show();



    }




    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = ProfileActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ProfileActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = ProfileActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
