package com.pb.hackathon;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext(){
        return mContext;
    }


    public static boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null) {
            isConnected = networkInfo.isConnected();
        }
        return isConnected;
    }
}
