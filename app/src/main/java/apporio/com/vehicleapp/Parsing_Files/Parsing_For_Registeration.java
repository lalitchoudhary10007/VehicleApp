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
import apporio.com.vehicleapp.Setter_Getter_Files.Registration_Result;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;
import apporio.com.vehicleapp.Singleton.VolleySingleton;
import apporio.com.vehicleapp.StartUpScreens.SignUpActivity;
import apporio.com.vehicleapp.StartUpScreens.SplashActivity;

/**
 * Created by admin on 11/19/2015.
 */
public class Parsing_For_Registeration {

    public static RequestQueue queue;
    public static StringRequest request;
    public static MyDatastore datastore;

    public static ProgressDialog pDialog;

    public static void RegisterParse(final Context activity,String mail,String mob,String passwrd,String name){

        String registerurl = all_Api_s.Register.concat(mail).concat(all_Api_s.Register2).concat(mob).concat(all_Api_s.Register3).concat(passwrd)
                .concat(all_Api_s.Register4).concat(name);
        registerurl=registerurl.replace(" ", "%20");

        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(activity))
                .create(MyDatastore.class);


        Log.e("registrurl", "" + registerurl);

        queue= VolleySingleton.getInstance(activity).getRequestQueue();
       request=new StringRequest(Request.Method.POST, registerurl, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {

               Log.e("response", "registration response" + response);

               pDialog.dismiss();
    try {

    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    Registration_Result resultUser = new Registration_Result();
    resultUser = gson.fromJson(response, Registration_Result.class);
    String Regresult = resultUser.result;


    if (resultUser.result.equals("1")) {

       datastore.USER_ID().put(resultUser.userinfo.user_id);
       datastore.USER_NAME().put(resultUser.userinfo.name);
       datastore.USER_EMAIL().put(resultUser.userinfo.email);
       datastore.USER_PHONE().put(resultUser.userinfo.phone_number);

//        if (SplashActivity.fbimage.equals("")){
//            datastore.USER_IMAGE().put("0");
//        }else {
//            datastore.USER_IMAGE().put(SplashActivity.fbimage);
//        }


        SharedPreferences.Editor edit2 = SignUpActivity.prefs.edit();
        edit2.putBoolean("pref_previously_started", Boolean.TRUE);
        edit2.commit();

        Intent in = new Intent(activity, MainActivity.class);
        activity.startActivity(in);

        Toast.makeText(activity, "Registration Successfully", Toast.LENGTH_SHORT).show();
       SignUpActivity.signup.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        SignUpActivity.signup.finish();
       SplashActivity.contexty.finish();
    } else {

        Toast.makeText(activity, "" + resultUser.msg, Toast.LENGTH_SHORT).show();
    }
}catch (Exception e){
    Log.e("Exception", "registration" + e);
}
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Log.e("response", "registration error" + error);
               pDialog.dismiss();
           }
       });
      request.setRetryPolicy(new DefaultRetryPolicy(50000,
              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
       queue.add(request);

    }


}
