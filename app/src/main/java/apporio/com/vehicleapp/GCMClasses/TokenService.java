package apporio.com.vehicleapp.GCMClasses;

import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.lacronicus.easydatastorelib.DatastoreBuilder;

import apporio.com.vehicleapp.Parsing_Files.Parsing_for_Add_Device_Id;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;

/**
 * Created by apporioinfolabs on 16-09-2016.
 */
public class TokenService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("notification", refreshedToken);
      //  sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        MyDatastore  datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(TokenService.this))
                .create(MyDatastore.class);

    }


}
