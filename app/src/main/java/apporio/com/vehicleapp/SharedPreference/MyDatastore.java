package apporio.com.vehicleapp.SharedPreference;

import com.lacronicus.easydatastorelib.Preference;
import com.lacronicus.easydatastorelib.StringEntry;

/**
 * Created by admin on 12/24/2015.
 */
public interface MyDatastore {

    @Preference("USER_NAME")
    StringEntry USER_NAME();

    @Preference("USER_EMAIL")
    StringEntry USER_EMAIL();

    @Preference("USER_ID")
    StringEntry USER_ID();

    @Preference("USER_PHONE")
    StringEntry USER_PHONE();

    @Preference("IS_USER_LOGIN")
    StringEntry IS_USER_LOGIN();

    @Preference("ABOUT_ME")
    StringEntry ABOUT_ME();

    @Preference("DEVICE_ID")
    StringEntry DEVICE_ID();

    @Preference("USER_IMAGE")
    StringEntry USER_IMAGE();



}
