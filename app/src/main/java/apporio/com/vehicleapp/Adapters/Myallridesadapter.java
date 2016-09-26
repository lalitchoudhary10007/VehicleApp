package apporio.com.vehicleapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apporio.com.vehicleapp.R;


public class Myallridesadapter extends BaseAdapter {

    Context ctc;
    LayoutInflater inflater;

    //String[] rid;
    ArrayList<String> carandno;
    ArrayList<String> date;
    ArrayList<String> Paddress;
    ArrayList<String> Daddress;
    ArrayList<String> carimages;
    ArrayList<String> driverimages;
    ArrayList<String> rupees;
    ArrayList<String> crno;
    //  ImageLoader il;

    public Myallridesadapter(Context context, ArrayList<String> c,  ArrayList<String> d, ArrayList<String> pa, ArrayList<String> da,ArrayList<String> ci,ArrayList<String> di,ArrayList<String> rs,ArrayList<String> cn) {
        ctc=context;
        carandno=c;
        date=d;
        Paddress=pa;
        Daddress=da;
        carimages=ci;
        driverimages=di;
        rupees=rs;
        crno=cn;

        inflater = LayoutInflater.from(this.ctc);
        //  il=new ImageLoader(ctc.getApplicationContext());
    }

    @Override
    public int getCount() {
        return carandno.size();
    }

    @Override
    public Object getItem(int position) {
        //Log.e("position", "Dish" + position);
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static   class Holder{

      public static TextView tv1,tv2,tv3,tv4,tv5,tv6;
        ImageView c1,d1;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.myrideslayout, null);
            holder = new Holder();
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.tv1 = (TextView) convertView.findViewById(R.id.cartype);
        holder.tv2 = (TextView) convertView.findViewById(R.id.daydate);
        holder.tv3 = (TextView) convertView.findViewById(R.id.pickaddrss);
        holder.tv4 = (TextView) convertView.findViewById(R.id.daddress);
        holder.tv5 = (TextView) convertView.findViewById(R.id.bill);
        holder.tv6 = (TextView) convertView.findViewById(R.id.carnmber);
        holder.c1=(ImageView)convertView.findViewById(R.id.carimg);
        holder.d1=(ImageView)convertView.findViewById(R.id.drvrimg);

        holder.tv1.setText(" "+carandno.get(position));
        holder.tv2.setText(date.get(position));
        holder.tv3.setText(Paddress.get(position));
        holder.tv4.setText(Daddress.get(position));
        holder.tv5.setVisibility(View.GONE);
      //  holder.tv5.setText("$ "+rupees.get(position));
        holder.tv6.setText(" -"+crno.get(position));

        //holder.c1.setImageResource(carimages[position]);
        Picasso.with(ctc).load("http://apporio.in/load_up_app/" + carimages.get(position)).into(holder.c1);
        Picasso.with(ctc).load("http://apporio.in/load_up_app/" + driverimages.get(position)).into(holder.d1);

       // holder.d1.setImageResource(driverimages[position]);

        return convertView;
    }
}