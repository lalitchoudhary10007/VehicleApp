package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 5/10/2016.
 */
public class Registration_Result {

    @SerializedName("result")
    public String result;

    @SerializedName("msg")
    public String msg;

    @SerializedName("user_info")
    public userinfo userinfo=new userinfo();


        public class userinfo{

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

            @SerializedName("status")
            public String status;


    }



}
