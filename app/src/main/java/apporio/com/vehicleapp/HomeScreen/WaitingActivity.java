package apporio.com.vehicleapp.HomeScreen;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.concurrent.TimeUnit;

import apporio.com.vehicleapp.NavigationActivities.MyRidesActivity;
import apporio.com.vehicleapp.R;

public class WaitingActivity extends Activity {

    private DonutProgress donutProgress;
    int j=0;
    public static WaitingActivity waitingActivity;
    LinearLayout waitingback ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        setContentView(R.layout.activity_waiting);

        waitingActivity = WaitingActivity.this;

        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        waitingback = (LinearLayout) findViewById(R.id.waitingback);
        donutProgress.setProgress(j);
        donutProgress.setText("01:00");
        donutProgress.setTextColor(Color.parseColor("#edc244"));
        final CounterClass timer = new CounterClass(1000*60*1, 1000);
        // textViewTime.setText("03:00");
        timer.start();
        donutProgress.setFinishedStrokeColor(Color.parseColor("#edc244"));
        donutProgress.setUnfinishedStrokeColor(Color.parseColor("#ffffff"));
        donutProgress.setMax(60 * 1);


        waitingback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent inn = new Intent(WaitingActivity.this , MyRidesActivity.class);
                inn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inn);
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            long millis = millisUntilFinished;

            String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);

            donutProgress.setText(hms);

            j++;

            donutProgress.setProgress(j);
        }

        @Override
        public void onFinish() {

            donutProgress.setText("Wait...");
           // toaccept.setText("Sorry !!!");
            donutProgress.setClickable(false);
            j++;

            donutProgress.setProgress(j);

            Toast.makeText(WaitingActivity.this, "When Your Ride Accepted Will Be Notified !!! ", Toast.LENGTH_SHORT).show();
            finish();
            Intent inn = new Intent(WaitingActivity.this , MyRidesActivity.class);
            inn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(inn);
            
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = WaitingActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(WaitingActivity.this.getResources().getColor(R.color.colorPrimary));
        } else {
            Window window = WaitingActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
