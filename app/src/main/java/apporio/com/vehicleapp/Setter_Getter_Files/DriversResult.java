package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/16/2016.
 */
public class DriversResult {


    @SerializedName("response")
    public driverResult2 response=new driverResult2();

    public class driverResult2 {

        @SerializedName("result")
        public String result;

        @SerializedName("Message")
        public List<driverDetails> Message=new ArrayList<driverDetails>();

    }


    public class driverDetails {

        @SerializedName("driver_id")
        public String driver_id;

        @SerializedName("driver_name")
        public String driver_name;

        @SerializedName("lat")
        public String lat;

        @SerializedName("long")
        public String longg;

        @SerializedName("car_type_name")
        public String car_type_name;

        @SerializedName("car_type_image")
        public String car_type_image;

        @SerializedName("car_number")
        public String car_number;

        @SerializedName("phone_number")
        public String phone_number;

        @SerializedName("driver_image")
        public String driver_image;

        @SerializedName("duration")
        public String duration;

        @SerializedName("distance")
        public String distance;

        @SerializedName("city_id")
        public String city_id;

    }


}
