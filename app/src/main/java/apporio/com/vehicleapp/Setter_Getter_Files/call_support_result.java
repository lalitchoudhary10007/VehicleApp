package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 5/11/2016.
 */
public class call_support_result {

    @SerializedName("result")
    public String result;


    @SerializedName("Message")
    public calldetails Message=new calldetails();

    public class calldetails {

        @SerializedName("title")
        public String title;

        @SerializedName("phone_number")
        public String phone_number;
    }

}
