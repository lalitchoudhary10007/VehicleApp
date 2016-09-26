package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 5/19/2016.
 */
public class RideInfoResult {

    @SerializedName("result")
    public String result;

    @SerializedName("Message")
    public RideInfoDetails Message=new RideInfoDetails();

    public class RideInfoDetails {

        @SerializedName("done_ride_id")
        public String done_ride_id;

        @SerializedName("ride_id")
        public String ride_id;

        @SerializedName("begin_lat")
        public String begin_lat;

        @SerializedName("begin_long")
        public String begin_long;

        @SerializedName("end_lat")
        public String end_lat;

        @SerializedName("end_long")
        public String end_long;

        @SerializedName("begin_address")
        public String begin_address;

        @SerializedName("end_address")
        public String end_address;

        @SerializedName("done_date")
        public String done_date;

        @SerializedName("driver_id")
        public String driver_id;

        @SerializedName("amount")
        public String amount;

        @SerializedName("distance")
        public String distance;

        @SerializedName("tot_time")
        public String tot_time;

        @SerializedName("driver_name")
        public String driver_name;

        @SerializedName("car_number")
        public String car_number;

        @SerializedName("phone_number")
        public String phone_number;

        @SerializedName("driver_email")
        public String driver_email;

        @SerializedName("password")
        public String password;

        @SerializedName("current_lat")
        public String current_lat;

        @SerializedName("current_long")
        public String current_long;

        @SerializedName("driver_image")
        public String driver_image;

        @SerializedName("car_type_id")
        public String car_type_id;

        @SerializedName("city_id")
        public String city_id;

        @SerializedName("car_name")
        public String car_name;

        @SerializedName("d_id")
        public String d_id;

        @SerializedName("flag")
        public String flag;

        @SerializedName("online_offline")
        public String online_offline;

        @SerializedName("status")
        public String status;

    }




}
