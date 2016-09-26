package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/16/2016.
 */
public class DriversResponse {

    @SerializedName("response")
    public driverResult1 response=new driverResult1();

    public class driverResult1 {

        @SerializedName("result")
        public String result;


    }


}
