package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 5/11/2016.
 */
public class ridedestsettergetter {

    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public Innerdestination innerdestination = new Innerdestination();

    public class Innerdestination {


        @SerializedName("geometry")
        public Geometry geometry = new Geometry();


        public class Geometry {
            @SerializedName("location")
            public Location11 location11 = new Location11();


            public class Location11 {
                @SerializedName("lat")
                public String lat;

                @SerializedName("lng")
                public String lng;
            }
        }

    }

}
