package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 5/10/2016.
 */
public class Login_Result {

    @SerializedName("result")
    public String result;

    @SerializedName("msg")
    public String msg;

    @SerializedName("prospect_details")
    public ProspectDetails prospectDetails=new ProspectDetails();


    public class ProspectDetails {

        @SerializedName("user_id")
        public String user_id;

        @SerializedName("name")
        public String name;

        @SerializedName("email")
        public String email;

        @SerializedName("password")
        public String password;

        @SerializedName("phone_number")
        public String phone_number;

        @SerializedName("d_id")
        public String d_id;

        @SerializedName("flag")
        public String flag;

        @SerializedName("status")
        public String status;

    }


}
