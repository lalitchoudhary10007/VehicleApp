package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaurav on 5/14/2016.
 */
public class PaypalResponse_setter_getter {

    @SerializedName("response")
    public  PaymentDetails response = new PaymentDetails();

    @SerializedName("details")
    public  Clientdetails details = new Clientdetails();

    public class PaymentDetails{
        @SerializedName("create_time")
        public   String create_time;

        @SerializedName("id")
        public   String  ids;

        @SerializedName("intent")
        public   String  intents;

        @SerializedName("state")
        public   String  state;

    }

    public class Clientdetails{

        @SerializedName("platform")
        public   String platform;

    }

}