package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 5/16/2016.
 */
public class RateCardResponse {

    @SerializedName("response")
    public RateCardResult rateCardResult=new RateCardResult();

    public class RateCardResult {

        @SerializedName("result")
        public String result;

        @SerializedName("Message")
        public RateCardDetails rateCardDetails=new RateCardDetails();


        public class RateCardDetails {

            @SerializedName("rate_card_id")
            public String rate_card_id;

            @SerializedName("car_type")
            public String car_type;

            @SerializedName("car_type_name")
            public String car_type_name;

            @SerializedName("car_type_image")
            public String car_type_image;

            @SerializedName("car_capacity")
            public String car_capacity;

            @SerializedName("minimum_rate")
            public String minimum_rate;

            @SerializedName("per_mile_rate")
            public String per_mile_rate;

            @SerializedName("per_minute_rate")
            public String per_minute_rate;

            @SerializedName("pick_up_rate")
            public String pick_up_rate;
        }


    }

}
