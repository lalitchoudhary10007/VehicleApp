package apporio.com.vehicleapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lacronicus.easydatastorelib.DatastoreBuilder;

import apporio.com.vehicleapp.HomeScreen.DriverdetailsActivity;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_currentOrder;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;
import apporio.com.vehicleapp.views.ProgressWheel;


public class CurrentOrderFragment extends Fragment {

    public static FrameLayout first_Frame,second_Frame,third_Frame,forth_Frame,fifth_Frame;
    public static TextView first_first_txt,first_s_txt,first_t_txt,first_ago,
                           second_first_txt,second_s_txt,second_t_txt,second_ago,
            third_first_txt,third_s_txt,third_t_txt,third_ago,
            forth_first_txt,forth_s_txt,forth_t_txt,forth_ago,
            fifth_first_txt,fifth_s_txt,fifth_t_txt,fifth_ago;

    MyDatastore datastore;

    public static ProgressWheel barinallrides;
    public static TextView Norides;
    public static LinearLayout statuslayout,receipentLayout;
    public static ImageView shipimage;
    public static TextView rname,raddress,rphone,remail;


    public static CurrentOrderFragment newInstance(String param1, String param2) {
        CurrentOrderFragment fragment = new CurrentOrderFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_current_order, container, false);

        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(getActivity()))
                .create(MyDatastore.class);
        Norides = (TextView)v.findViewById(R.id.allnocurrentrides);
        barinallrides=(ProgressWheel)v.findViewById(R.id.progressBarcurrent);
        statuslayout=(LinearLayout)v.findViewById(R.id.statusLayout);
        receipentLayout=(LinearLayout)v.findViewById(R.id.receipentLayout);
        shipimage = (ImageView) v.findViewById(R.id.shipimage);


        init(v);

        Parsing_for_currentOrder.parseMyRidess(getActivity(),datastore.USER_ID().get(),"1");






        return v;
    }




    public void init(View v){

        rname=(TextView)v.findViewById(R.id.namer);
        raddress=(TextView)v.findViewById(R.id.addressr);
        remail=(TextView)v.findViewById(R.id.namer);
        rphone=(TextView)v.findViewById(R.id.phoner);

        first_Frame=(FrameLayout)v.findViewById(R.id.first_frame);
        first_first_txt=(TextView)v.findViewById(R.id.first_first_txt);
        first_s_txt=(TextView)v.findViewById(R.id.first_second_txt);
        first_t_txt=(TextView)v.findViewById(R.id.first_time);
        first_ago=(TextView)v.findViewById(R.id.first_ago);

        second_Frame=(FrameLayout)v.findViewById(R.id.second_frame);
        second_first_txt=(TextView)v.findViewById(R.id.second_first_txt);
        second_s_txt=(TextView)v.findViewById(R.id.second_second_txt);
        second_t_txt=(TextView)v.findViewById(R.id.second_time);
        second_ago=(TextView)v.findViewById(R.id.second_ago);

        third_Frame=(FrameLayout)v.findViewById(R.id.third_frame);
        third_first_txt=(TextView)v.findViewById(R.id.third_first_txt);
        third_s_txt=(TextView)v.findViewById(R.id.third_second_txt);
        third_t_txt=(TextView)v.findViewById(R.id.third_time);
        third_ago=(TextView)v.findViewById(R.id.third_ago);

        forth_Frame=(FrameLayout)v.findViewById(R.id.forth_frame);
        forth_first_txt=(TextView)v.findViewById(R.id.forth_first_txt);
        forth_s_txt=(TextView)v.findViewById(R.id.forth_second_txt);
        forth_t_txt=(TextView)v.findViewById(R.id.forth_time);
        forth_ago=(TextView)v.findViewById(R.id.forth_ago);

        fifth_Frame=(FrameLayout)v.findViewById(R.id.fifth_frame);
        fifth_first_txt=(TextView)v.findViewById(R.id.fifth_first_txt);
        fifth_s_txt=(TextView)v.findViewById(R.id.fifth_second_txt);
        fifth_t_txt=(TextView)v.findViewById(R.id.fifth_time);
        fifth_ago=(TextView)v.findViewById(R.id.fifth_ago);

    }




}
