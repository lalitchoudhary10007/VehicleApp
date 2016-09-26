package apporio.com.vehicleapp.StartUpScreens;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import apporio.com.vehicleapp.Parsing_Files.Parsing_For_Registeration;
import apporio.com.vehicleapp.R;

public class SignUpActivity extends Activity {

    EditText name,email,password,mobile;
    View Vname1,Vname2,Vmail1,Vmail2,Vpass1,Vpass2,Vmob1,Vmob2;
    public static SharedPreferences prefs;
    public static SignUpActivity signup;
    TextView signView;

    ImageView signupFB;
    CallbackManager mCallbackManager;
    public int RC_SIGN_IN = 99 ;

    public static String fbname="",fbmail="";
    public static String fbimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success", "Login");
                if (AccessToken.getCurrentAccessToken() != null) {
                    getFacebookData();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignUpActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(SignUpActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        setContentView(R.layout.activity_sign_up);

        signup=SignUpActivity.this;

        printKeyHash(SignUpActivity.this);


        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pass);
        mobile=(EditText)findViewById(R.id.mobile);

        Vname1=(View)findViewById(R.id.viewname1);
        Vname2=(View)findViewById(R.id.viewname2);
        Vmail1=(View)findViewById(R.id.mailview1);
        Vmail2=(View)findViewById(R.id.mailview2);
        Vpass1=(View)findViewById(R.id.passview1);
        Vpass2=(View)findViewById(R.id.passview2);
        Vmob1=(View)findViewById(R.id.mobview1);
        Vmob2=(View)findViewById(R.id.mobview2);

        signupFB=(ImageView)findViewById(R.id.facebook_image);

        signView=(TextView)findViewById(R.id.signup);



        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    Vname1.setVisibility(View.GONE);
                    Vname2.setVisibility(View.VISIBLE);
                }
                else{
                    Vname1.setVisibility(View.VISIBLE);
                    Vname2.setVisibility(View.GONE);
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    Vmail1.setVisibility(View.GONE);
                    Vmail2.setVisibility(View.VISIBLE);
                }
                else{
                    Vmail1.setVisibility(View.VISIBLE);
                    Vmail2.setVisibility(View.GONE);
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
        mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    Vmob1.setVisibility(View.GONE);
                    Vmob2.setVisibility(View.VISIBLE);
                }
                else{
                    Vmob1.setVisibility(View.VISIBLE);
                    Vmob2.setVisibility(View.GONE);
                }
            }
        });


        signView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (mail.matches(emailPattern)) {
                    Parsing_For_Registeration.RegisterParse(signup, mail, mobile.getText().toString().trim(), password.getText().toString().trim(), name.getText().toString().trim());
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                }

            }
        });

signupFB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        LoginManager.getInstance().logInWithReadPermissions(SignUpActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
    }
});


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = SignUpActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(SignUpActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = SignUpActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            // getGoogleAccountdata(result);  implement code of linked in here

        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getFacebookData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null){
                        Profile profile1 = Profile.getCurrentProfile();
//                        fbimage=String.valueOf(profile1.getProfilePictureUri(50,50));
//                         Log.e("image",""+fbimage);
                        Log.e("profile image fb",""+Profile.getCurrentProfile());
                        Log.e("email",""+json.getString("email"));
                        Log.e("name",""+json.getString("name"));
                        fbname=json.getString("name");
                        fbmail=json.getString("email");

                        name.setText(fbname);
                        email.setText(fbmail);

                      //  startActivity(new Intent(contexty,SignUpActivity.class));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();

    }




}
