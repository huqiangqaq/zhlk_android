package com.enjoyor.soft.product.cyweight.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;

/**
 * Created by huqiang on 2016/4/12 10:59.
 */
public class LongRunningService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //后台访问数据，获得weight
                Log.d("LongRunningService","execute at "+new Date().toString());
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = 5*1000;
        long triggerAtTime = SystemClock.elapsedRealtime()+time;
        Intent i = new Intent("com.android.broadcast.RECEIVER_ACTION");
        i.putExtra("data",Math.random()*10+"");
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

}
