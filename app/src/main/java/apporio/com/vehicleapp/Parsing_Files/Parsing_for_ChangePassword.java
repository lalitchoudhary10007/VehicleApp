package apporio.com.vehicleapp.Parsing_Files;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.NavigationActivities.ProfileActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.Change_pass_Result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 11/20/2015.
 */
public class Parsing_for_ChangePassword {

    public static RequestQueue queue;
    public static StringRequest request;

    public static ProgressDialog pDialog;

    public static void ParseChangepassword(final Context changepass,String id,String newpass,String oldpass){

        String changepassURL= all_Api_s.changepassword.concat(id).concat(all_Api_s.changepassword1).concat(newpass).concat(all_Api_s.changepassword2)
                .concat(oldpass);
        changepassURL=changepassURL.replace(" ", "%20");
        Log.e("change password url", "" + changepassURL);

        pDialog = new ProgressDialog(changepass);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        queue= VolleySingleton.getInstance(changepass).getRequestQueue();
        request=new StringRequest(Request.Method.GET, changepassURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "change password response" + response);
                pDialog.dismiss();
                try {
                    GsonBuilder builder=new GsonBuilder();
                    Gson gson=builder.create();

                   Change_pass_Result cngpassresultCore=new Change_pass_Result();
                    cngpassresultCore=gson.fromJson(response,Change_pass_Result.class);

               String chngepassresult=cngpassresultCore.result;
               String chngepassmessage=cngpassresultCore.msg;
               Log.e("new password", "" + cngpassresultCore.new_password);

                    if (chngepassresult.equals("1")){
                        Toast.makeText(changepass, "" + chngepassmessage, Toast.LENGTH_LONG).show();
                        ProfileActivity.dialog.dismiss();

                    }else {
                        Toast.makeText(changepass, "" + chngepassmessage, Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    Log.e("error", "change password exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.e("error", "change password error" + error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue.add(request);

    }

    }


