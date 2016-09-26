package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/13/2016.
 */
public class View_Cars_Result {


    @SerializedName("result")
    public String result;

    @SerializedName("Message")
    public List<CarTypeDetails> cityDetailsList=new ArrayList<CarTypeDetails>();

    public class CarTypeDetails {

        @SerializedName("car_type_id")
        public String car_type_id;

        @SerializedName("car_type_name")
        public String car_type_name;

        @SerializedName("car_type_image")
        public String car_type_image;
    }

}
