package apporio.com.vehicleapp.GCMClasses;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import apporio.com.vehicleapp.Api_Urls.all_Api_s;
import apporio.com.vehicleapp.HomeScreen.DriverdetailsActivity;
import apporio.com.vehicleapp.HomeScreen.WaitingActivity;
import apporio.com.vehicleapp.NavigationActivities.BillActivity;
import apporio.com.vehicleapp.NavigationActivities.MyRidesActivity;
import apporio.com.vehicleapp.R;
import apporio.com.vehicleapp.Setter_Getter_Files.RideInfoResult;
import apporio.com.vehicleapp.Setter_Getter_Files.View_Driver_Result;
import apporio.com.vehicleapp.Singleton.VolleySingleton;
import apporio.com.vehicleapp.logger.Logger;

/**
 * Created by admin on 2/15/2016.
 */
public class FCMNotificationIntentService extends FirebaseMessagingService {

    public static final int notifyID = 9001;
    String Rideid;
    String r;
    Intent resultIntent;

    public static double driverlat,driverlang;

    public static String RideID,cartype_id,cityid,driverImage,DriverID,amount,dist,rtime,drnmae,address1,address2;



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Logger.e("notification -- " + remoteMessage.getNotification().getBody());

        //String msg=data.getString(""+GCMConstants.MSG_KEY);

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e("", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e("", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e("", "Message Notification Body: " + remoteMessage.getNotification().getBody());

            try {

                Intent i=new Intent(FCMNotificationIntentService.this,MyRidesActivity.class);

                sendNotification(remoteMessage.getNotification().getBody(),i);

                if (remoteMessage.getNotification().getBody().equals("Your ride accepted and driver is on the way")){

                    Intent iiii=new Intent(FCMNotificationIntentService.this,MyRidesActivity.class);

                    sendNotification("Your Ride Accepted !!",iiii);

                }
                else if (remoteMessage.getNotification().getBody().equals("Your ride have prepared")){
                    Intent i1=new Intent(FCMNotificationIntentService.this,MyRidesActivity.class);

                    sendNotification("Driver Started Ride And On The Way",i1);
                }

                else if (remoteMessage.getNotification().getBody().equals("Driver arrived at your doorstep")){

                    Intent i1=new Intent(FCMNotificationIntentService.this,MyRidesActivity.class);

                    sendNotification(remoteMessage.getNotification().getBody(),i1);

                } else if (remoteMessage.getNotification().getBody().equals("Ride begin")){
                    Intent i1=new Intent(FCMNotificationIntentService.this,MyRidesActivity.class);
                    sendNotification(remoteMessage.getNotification().getBody(),i1);
                }
                else {
                    String[] parts= remoteMessage.getNotification().getBody().split("_");
                    RideID=parts[1];
                    r=parts[0];
                    Logger.e("iddddd" + RideID + "first part" + r);

                    //   DriverDetail(FCMNotificationIntentService.this, RideID);

                    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                    List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
                    Log.e("current task :", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClass().getSimpleName());
                    ComponentName componentInfo = taskInfo.get(0).topActivity;
                    if(componentInfo.getPackageName().equalsIgnoreCase("apporio.com.vehicleapp")){
                        //Activity in foreground, broadcast intent
                        Toast.makeText(FCMNotificationIntentService.this, "App is running", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //Activity Not Running
                        //Generate Notification
                        Toast.makeText(FCMNotificationIntentService.this, "App not running", Toast.LENGTH_SHORT).show();
                    }


                    Intent ii=new Intent(FCMNotificationIntentService.this,MyRidesActivity.class);
                    sendNotification("Your Ride Accepted !!!",ii);
                    WaitingActivity.waitingActivity.finish();
                }

            }catch (Exception e){
                Log.e("exception",""+e);
            }





        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.




        super.onMessageReceived(remoteMessage);
    }





    public void sendNotification(String message, Intent i) {

      resultIntent = i;

//        else {
//            resultIntent = new Intent(this, MyRidesActivity.class);
//        }  if (r.equals("Your Ride Completed !!")){
//            resultIntent = new Intent(this, BillActivity.class);
//        }



        resultIntent.putExtra("msg", message);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mNotifyBuilder;
        NotificationManager mNotificationManager;

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Vehicle App ")
                .setContentText("New Ride " + message)
                .setSmallIcon(R.drawable.logo);

        // Set pending intent
        mNotifyBuilder.setContentIntent(resultPendingIntent);

        // Set Vibrate, Sound and Light
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;

        mNotifyBuilder.setDefaults(defaults);
        mNotifyBuilder.setContentText(""+message);
        mNotifyBuilder.setAutoCancel(true);
        mNotificationManager.notify(notifyID, mNotifyBuilder.build());

    }


    public void DriverDetail(final Context Ridenow, String rideid  ){


        String RideNowURL= all_Api_s.View_Driver.concat(rideid);


        RideNowURL=RideNowURL.replace(" ","%20");
        Log.e("ride url", "" + RideNowURL);

        RequestQueue queue = VolleySingleton.getInstance(Ridenow).getRequestQueue();

//        pDialog = new ProgressDialog(Ridenow);
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(false);


        queue= VolleySingleton.getInstance(Ridenow).getRequestQueue();
        StringRequest request=new StringRequest(Request.Method.GET, RideNowURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("respons Ride now confrm", "" + response);
              //  pDialog.dismiss();
                try {
                    GsonBuilder builder=new GsonBuilder();
                    Gson gson=builder.create();

                    View_Driver_Result driverinfo=new View_Driver_Result();
                    driverinfo=gson.fromJson(response,View_Driver_Result.class);

                    if (driverinfo.getResult()==1){

                        Intent ii=new Intent(Ridenow,MyRidesActivity.class);

                        DriverdetailsActivity. time=  driverinfo .getMessage().getR_time();
                        DriverdetailsActivity. driverid=driverinfo .getMessage().getDriver_id();
                        driverlat= Double.valueOf(driverinfo .getMessage().getCurrent_lat());
                        driverlang=Double.valueOf(driverinfo.getMessage().getCurrent_long());
                        DriverdetailsActivity. drivernme.setText(driverinfo .getMessage().getDriver_image());
                        DriverdetailsActivity . drivercarno.setText(driverinfo .getMessage().getCar_number());
                        DriverdetailsActivity . drivercarname.setText(driverinfo .getMessage().getCar_type_name());
                        DriverdetailsActivity. im=driverinfo .getMessage().getDriver_image();
                        DriverdetailsActivity. star="5";
                        DriverdetailsActivity . ratingBarGreen.setScore(Float.parseFloat(DriverdetailsActivity.star));
                        Picasso.with(Ridenow).load("http://apporio.in/load_up_app/"+DriverdetailsActivity. im)
                                .error(R.drawable.defaultprof)
                                .placeholder(R.drawable.defaultprof)
                                .into(DriverdetailsActivity.driverimage);

                        WaitingActivity.waitingActivity.finish();

                        sendNotification("Driver Allocated !!",ii);

                    }
                    else{
                        Toast.makeText(Ridenow, "Required Field Missing !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Log.e("Exception", "ridenow confrm Exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  pDialog.dismiss();
                Log.e("error in ridenow confrm",""+error);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // pDialog.show();
        queue.add(request);

    }


}
