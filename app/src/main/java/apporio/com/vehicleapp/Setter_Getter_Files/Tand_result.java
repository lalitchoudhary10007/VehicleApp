package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 5/11/2016.
 */
public class Tand_result {


    @SerializedName("result")
    public String result;

    @SerializedName("Message")
    public InnerCore innerCore=new InnerCore();

    public class InnerCore {

        @SerializedName("t_and_c")
        public String t_and_c;

    }

}
