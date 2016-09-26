package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 5/11/2016.
 */
public class AboutUs_Result {


    @SerializedName("result")
    public String result;

    @SerializedName("Message")
    public title Message=new title();

    public class title {

        @SerializedName("title")
        public String title;


        @SerializedName("desc")
        public String desc;
    }



}
