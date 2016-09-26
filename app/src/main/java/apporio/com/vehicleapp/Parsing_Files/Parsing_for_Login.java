package apporio.com.vehicleapp.Parsing_Files;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lacronicus.easydatastorelib.DatastoreBuilder;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.MainActivity;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.Login_Result;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;
import apporio.com.vehicleapp.Singleton.VolleySingleton;
import apporio.com.vehicleapp.StartUpScreens.LoginActivity;
import apporio.com.vehicleapp.StartUpScreens.SplashActivity;

/**
 * Created by admin on 11/19/2015.
 */
public class Parsing_for_Login {

    public static RequestQueue queue;
    public static StringRequest request;
    public static MyDatastore datastore;
    public static ProgressDialog pDialog;
    public static String username,usermail;

    public static void LoginParse(final Context loginactivity,String phone,String pass,String flag){

        String Loginurl= all_Api_s.LoginEmail.concat(phone).concat(all_Api_s.LoginEmail1).concat(pass).concat(all_Api_s.LoginEmail2).concat(flag);
        Loginurl=Loginurl.replace(" ","%20");
        Log.e("loginurl", "" + Loginurl);

        pDialog = new ProgressDialog(loginactivity);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(loginactivity))
                .create(MyDatastore.class);

        queue= VolleySingleton.getInstance(loginactivity).getRequestQueue();

        request=new StringRequest(Request.Method.POST, Loginurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
         Log.e("login response", "" + response);

                pDialog.dismiss();

                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();

                Login_Result resultCore=new Login_Result();
                resultCore=gson.fromJson(response,Login_Result.class);


                 username=resultCore.prospectDetails.name;
                 usermail=resultCore.prospectDetails.email;


                if(resultCore.result.equals("1")){

                    username=resultCore.prospectDetails.name;
                    usermail=resultCore.prospectDetails.email;

                    datastore.USER_ID().put(resultCore.prospectDetails.user_id);
                    datastore.USER_NAME().put(resultCore.prospectDetails.name);
                    datastore.USER_EMAIL().put(resultCore.prospectDetails.email);
                    datastore.USER_PHONE().put(resultCore.prospectDetails.phone_number);


                    Toast.makeText(loginactivity, "" + resultCore.msg, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor edit2 = LoginActivity.prefs.edit();
                    edit2.putBoolean("pref_previously_started", true);
                    edit2.commit();

                   // Parsing_for_Add_Device_Id.Add_Device_ID(loginactivity,userid,Splashactivity.regId,"2");

                    Intent in = new Intent(loginactivity, MainActivity.class);
                    loginactivity.startActivity(in);
                    LoginActivity.loginactivity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    LoginActivity.loginactivity.finish();
                    SplashActivity.contexty.finish();
                }
                else {

                    Toast.makeText(loginactivity, "" + resultCore.msg, Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
         Log.e("login error", "" + error);
             pDialog.dismiss();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        pDialog.show();
        queue.add(request);
    }
}
