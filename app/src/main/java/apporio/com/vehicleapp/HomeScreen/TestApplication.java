package apporio.com.vehicleapp.HomeScreen;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Lenovo on 9/8/2016.
 */
public class TestApplication extends Application implements Application.ActivityLifecycleCallbacks {
    boolean applicationOnPause = false;


    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.e("create","onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e("start","onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        applicationOnPause = false;
        Log.e("resume","onActivityResumed "+activity.getClass());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        applicationOnPause = true;
        Log.e("paused","onActivityPaused "+activity.getClass());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e("stopped","onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.e("save instance state","onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e("destroy","onActivityDestroyed ");
    }
}
