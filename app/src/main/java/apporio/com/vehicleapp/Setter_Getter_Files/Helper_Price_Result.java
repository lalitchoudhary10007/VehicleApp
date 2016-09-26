package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 6/15/2016.
 */
public class Helper_Price_Result {

  @SerializedName("result")
    public String result;


    @SerializedName("Message")
    public List<Helper_Price_Details> Message=new ArrayList<Helper_Price_Details>();


    public class Helper_Price_Details{

     @SerializedName("Helper_price")
        public String Helper_price;

    }


}
