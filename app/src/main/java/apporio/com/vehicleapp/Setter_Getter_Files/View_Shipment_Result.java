package apporio.com.vehicleapp.Setter_Getter_Files;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apporio5 on 13-07-2016.
 */
public class View_Shipment_Result {

    @SerializedName("result")
    public String result;

    @SerializedName("Message")
    public List<Shipment_Details> Message=new ArrayList<Shipment_Details>();


    public class Shipment_Details{

        @SerializedName("shipment_id")
        public String shipment_id;

        @SerializedName("shipment_weight")
        public String shipment_weight;

        @SerializedName("shipment_image")
        public String shipment_image;

        @SerializedName("shipment_quantity")
        public String shipment_quantity;

        @SerializedName("shipment_spcl_instruction")
        public String shipment_spcl_instruction;

    }


}
