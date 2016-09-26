package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 6/15/2016.
 */
public class View_Weight_Result {

    @SerializedName("result")
    public String result;

    @SerializedName("Message")
    public List<weightDetails> Message=new ArrayList<weightDetails>();


    public class weightDetails{

        @SerializedName("weight_range")
        public String weight_range;

        @SerializedName("status")
        public String status;

    }


}
