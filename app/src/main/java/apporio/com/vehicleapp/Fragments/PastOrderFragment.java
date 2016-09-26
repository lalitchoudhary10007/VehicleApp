package apporio.com.vehicleapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lacronicus.easydatastorelib.DatastoreBuilder;

import apporio.com.vehicleapp.Parsing_Files.Parsing_for_ViewMyrides;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;
import apporio.com.vehicleapp.views.ProgressWheel;


public class PastOrderFragment extends Fragment {


    public static ListView RidesList;
    MyDatastore datastore;
    public static ProgressWheel barinallrides;
    public static TextView Norides;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_past_order, container, false);

        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(getActivity()))
                .create(MyDatastore.class);

        Norides = (TextView)v.findViewById(R.id.allnorides);
        RidesList=(ListView)v.findViewById(R.id.ridesListview);
        barinallrides=(ProgressWheel)v.findViewById(R.id.progressBar2);

        Parsing_for_ViewMyrides.parseMyRides(getActivity(),datastore.USER_ID().get(),"2");


        return v;
    }


}
