package apporio.com.vehicleapp.Parsing_Files;

import android.content.Context;
import android.preference.PreferenceManager;
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
import com.lacronicus.easydatastorelib.DatastoreBuilder;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.NavigationActivities.ProfileActivity;
import apporio.com.vehicleapp.Setter_Getter_Files.EditProfileResult;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;
import apporio.com.vehicleapp.Singleton.VolleySingleton;

/**
 * Created by admin on 11/19/2015.
 */
public class Parsing_for_Editprofile {

    public static RequestQueue queue;
    public static StringRequest request;
    public static MyDatastore datastore;

    public static void parseEditProfile(final Context EditprofileActivity,String userId,String name,String phoneno){

    String EditprofileURL= all_Api_s.EditProfile.concat(userId).concat(all_Api_s.EditProfile1).concat(name).concat(all_Api_s.EditProfile2).concat(phoneno);
    EditprofileURL=EditprofileURL.replace(" ","%20");
        Log.e("edit profile url", "" + EditprofileURL);

        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(EditprofileActivity))
                .create(MyDatastore.class);

        queue= VolleySingleton.getInstance(EditprofileActivity).getRequestQueue();

        request=new StringRequest(Request.Method.POST, EditprofileURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
          Log.e("response", "in edit profile" + response);
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    EditProfileResult editProfileResult=new EditProfileResult();
                    editProfileResult=gson.fromJson(response,EditProfileResult.class);


                    if (editProfileResult.result.equals("1")){

                        datastore.USER_NAME().put(editProfileResult.editProfileDetails.name);
                        datastore.USER_PHONE().put(editProfileResult.editProfileDetails.phone_number);

                        Toast.makeText(EditprofileActivity, "" + editProfileResult.msg, Toast.LENGTH_LONG).show();


                        ProfileActivity.profileActivity.finish();
                    }else {
                        Toast.makeText(EditprofileActivity, "" + editProfileResult.msg, Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    Log.e("excetion", "editprofile" + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "in edit profile" + error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

}
