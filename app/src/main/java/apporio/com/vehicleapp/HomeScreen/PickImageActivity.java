package apporio.com.vehicleapp.HomeScreen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lacronicus.easydatastorelib.DatastoreBuilder;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.NavigationActivities.RideEstimateResultCostActivity;
import apporio.com.vehicleapp.Parsing_Files.Parsing_View_Weight;
import apporio.com.vehicleapp.Parsing_Files.Parsing_for_view_Helper_price;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.AddShipmentResponse;
import apporio.com.vehicleapp.Setter_Getter_Files.DriverInfoResult;
import apporio.com.vehicleapp.SharedPreference.MyDatastore;
import apporio.com.vehicleapp.views.AdjustableImageView;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class PickImageActivity extends Activity {

    LinearLayout uploadImage,backLayout;
    int REQUEST_CODE=123;
    TextView done;

    FrameLayout uploadImageFrame;
    public static PickImageActivity pickImageActivity;

    MyDatastore datastore;
    EditText spclInstrunction;
    String special_instrunction;

    ArrayList<String> PickImages=new ArrayList<String>();
    AdjustableImageView Ship_Image;

    public static Spinner WeightSpinner,QuantitySpinner;
    String selectedWeightRange,selectedQuantity;

    String[] quantity={"1","2","3","4","5","6","7","8","9","10"};

    public static Dialog UploadImageDialog;
    private ProgressBar progressBar;
    private TextView txtPercentage;

    long totalSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_image);

        pickImageActivity=PickImageActivity.this;

        uploadImage=(LinearLayout)findViewById(R.id.uploadImagelayout);


        done=(TextView)findViewById(R.id.ridesestimate);

        spclInstrunction=(EditText)findViewById(R.id.spclinstrnctions);


        WeightSpinner=(Spinner)findViewById(R.id.weightList);
        QuantitySpinner=(Spinner)findViewById(R.id.quantity_list);


        Ship_Image=(AdjustableImageView) findViewById(R.id.shipImage);
        Ship_Image.setImageDrawable(getResources().getDrawable(R.drawable.banner1));


        uploadImageFrame=(FrameLayout)findViewById(R.id.uploadImageFrame);

        backLayout=(LinearLayout)findViewById(R.id.backinpickimage);

        datastore = new DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(pickImageActivity))
                .create(MyDatastore.class);


        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayAdapter ad= new ArrayAdapter(PickImageActivity.this, android.R.layout.simple_spinner_item,quantity);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        QuantitySpinner.setAdapter(ad);

        Parsing_View_Weight.View_weight(PickImageActivity.this);

        WeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectedWeightRange=parent.getItemAtPosition(position).toString();
              Log.e("selected weight",""+selectedWeightRange);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        QuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedQuantity=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(PickImageActivity.this);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                intent.setShowGif(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PickImages.isEmpty()){
                    Toast.makeText(PickImageActivity.this, "Plz Select Image !!!", Toast.LENGTH_SHORT).show();
                }else {

                    special_instrunction=spclInstrunction.getText().toString().trim();

                    Helper.ShipImages.add(PickImages.get(0));
                    Helper.ShipInstrunction.add(special_instrunction);
                    Helper.ShipWeight.add(selectedWeightRange);
                    Helper.ShipQunatity.add(selectedQuantity);

                    showUploadImageProgress();

                    new UploadFileToServer().execute();
                }




            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                PickImages=
                        data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

                Log.e("path of images",""+PickImages);

                String images= PickImages.get(0);
                File imgFile = new File(images);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 1000, 1000, true);
                Ship_Image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Ship_Image.setImageBitmap(scaled);
                Log.e("",""+Helper.ShipImages.size());
                Log.e("",""+Helper.ShipImages);
                uploadImage.setVisibility(View.GONE);

            }
        }
    }


    public void showUploadImageProgress(){

        UploadImageDialog = new Dialog(PickImageActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
        UploadImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=UploadImageDialog.getWindow();
        UploadImageDialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        UploadImageDialog.setContentView(R.layout.dialog_for_upload_image);

        txtPercentage=(TextView)UploadImageDialog.findViewById(R.id.txtPercentage);
        progressBar=(ProgressBar)UploadImageDialog.findViewById(R.id.progressBar);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.prgress_bar_color));

    }


    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            txtPercentage.setText("0");

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            //  progressBar.setVisibility(View.VISIBLE);
            UploadImageDialog.show();
            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(all_Api_s.Add_Shipment);
            Log.e("",""+all_Api_s.Add_Shipment);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });


               //  Adding file data to http body

                File sourceFile = new File(PickImages.get(0));

                entity.addPart("shipment_image", new FileBody(sourceFile));
                // Extra parameters if you want to pass to server


                entity.addPart("shipment_name",
                        new StringBody("Shipment Image"));
//                entity.addPart("shipment_quantity",
//                        new StringBody(selectedQuantity));
//                entity.addPart("shipment_spcl_instruction",
//                        new StringBody(special_instrunction));


                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                Log.e("","cerr"+e);
            } catch (IOException e) {
                Log.e("","errr"+e);
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("response", "Response from server: " + result);
            //progressBar.setVisibility(View.GONE);
            UploadImageDialog.dismiss();
            try {
                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();

                AddShipmentResponse ship_response=new AddShipmentResponse();
                ship_response=gson.fromJson(result,AddShipmentResponse.class);

                if (ship_response.result.toString().equals("1")){

                    Helper.ShipImagePath.add(ship_response.image_path);
                    Toast.makeText(PickImageActivity.this, ""+ship_response.msg, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(PickImageActivity.this, ""+ship_response.msg, Toast.LENGTH_SHORT).show();
                }



            }catch (Exception e){
                Log.e("Exception", "edit profile" + e);
            }


            super.onPostExecute(result);

        }

    }





    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = PickImageActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(PickImageActivity.this.getResources().getColor(R.color.color_primary));
        } else {
            Window window = PickImageActivity.this.getWindow();
            //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }



}
