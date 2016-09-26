package apporio.com.vehicleapp.GooglePlaces;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saifi45 on 10/3/2015.
 */
public class placessettergetter {
    @SerializedName("status")
    public String status;

    @SerializedName("predictions")

    public List<Innerplaces> predictionsplace= new ArrayList<Innerplaces>();
}
