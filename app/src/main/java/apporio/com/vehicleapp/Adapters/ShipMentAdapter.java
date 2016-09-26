package apporio.com.vehicleapp.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import apporio.com.vehicleapp.HomeScreen.Helper;
import apporio.com.vehicleapp.HomeScreen.ShipmentActivity;
import apporio.com.vehicleapp.Parsing_Files.Parsing_For_Delete_Shipment;
import apporio.com.vehicleapp.R;

/**
 * Created by admin on 5/5/2016.
 */
public class ShipMentAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> img;
    ArrayList<String> weight;
    ArrayList<String> instructions;
    ArrayList<String> qq;


    LayoutInflater inflater;

    public ShipMentAdapter(Context context, ArrayList<String> img, ArrayList<String> w, ArrayList<String> i, ArrayList<String> q) {
        this.context = context;
        this.img = img;
        this.weight = w;
        this.instructions = i;
        this.qq=q;

        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return img.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class Holder{

        public  TextView weight,instrunction,quantity;
        ImageView shipimage,delete_Ship;

    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.ship_items, null);

        // Locate the ImageView in viewpager_item.xml
        final Holder holder=new Holder();

        holder.instrunction=(TextView)itemView.findViewById(R.id.inst);
        holder.weight=(TextView)itemView.findViewById(R.id.weit);
        holder.quantity=(TextView)itemView.findViewById(R.id.quantt);
        holder. shipimage = (ImageView) itemView.findViewById(R.id.pagerimage);
        holder.delete_Ship=(ImageView)itemView.findViewById(R.id.close);
        holder. shipimage.setAdjustViewBounds(true);
        String images= img.get(position);
        File imgFile = new File(images);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        holder. shipimage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 482, 500, true);

        holder. shipimage.setImageBitmap(scaled);

    //    Picasso.with(context).load("http://keshavgoyal.com/load_up_app/" + img.get(position)).into(holder.shipimage);

        holder.instrunction.setText(instructions.get(position));
        holder.weight.setText(weight.get(position));
        holder.quantity.setText(qq.get(position));

        holder.delete_Ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

     //           Parsing_For_Delete_Shipment.Delete_Shipment(context,id.get(position));

                Helper.ShipImages.remove(position);
                Helper.ShipInstrunction.remove(position);
                Helper.ShipWeight.remove(position);

                notifyDataSetChanged();
                ShipmentActivity.setListViewHeightBasedOnChildren(ShipmentActivity.ShipListView);

                if (Helper.ShipImages.isEmpty()){
                    ShipmentActivity.Add_Shipment.setVisibility(View.VISIBLE);
                    ShipmentActivity.ShipListlayout.setVisibility(View.GONE);
                }else {
                    ShipmentActivity.Add_Shipment.setVisibility(View.GONE);
                    ShipmentActivity.ShipListlayout.setVisibility(View.VISIBLE);
                }

               ShipmentActivity.ShipmentJsonSend= ShipmentActivity.ShipmentJson();

            }
        });



        return itemView;

    }

}
