package apporio.com.vehicleapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

import apporio.com.vehicleapp.R;

/**
 * Created by admin on 5/23/2016.
 */
public class SplashPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Integer> flag;
    LayoutInflater inflater;

    public SplashPagerAdapter(Context context, ArrayList<Integer> flag) {
        this.context = context;
        this.flag = flag;

    }

    @Override
    public int getCount() {
        return flag.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imgflag;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.splash_pager_xml, container,false);

        // Locate the ImageView in viewpager_item.xml
        imgflag = (ImageView) itemView.findViewById(R.id.sppagerimage);

        imgflag.setImageResource(flag.get(position));

//  Toast.makeText(context, " " + images, Toast.LENGTH_SHORT).show();




        // Capture position and set to the ImageView

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }



}
