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

import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.product.cyweight.Utils.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Date;

import okhttp3.Call;

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
            String rfidCode = intent.getStringExtra("carnum");
                //后台访问数据，获得weight
                Log.d("LongRunningService","execute at "+new Date().toString());
                OkHttpUtils.get().url(Constants.WEIGHT_URL+rfidCode).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        String weight = JsonUtil.pareWeight(response);
                        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        int time = 2*1000;
                        long triggerAtTime = SystemClock.elapsedRealtime()+time;
                        Intent i = new Intent("com.android.broadcast.RECEIVER_ACTION");
                        i.putExtra("data",weight);
                        PendingIntent pi = PendingIntent.getBroadcast(LongRunningService.this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
                        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
                    }
                });
        return super.onStartCommand(intent, flags, startId);
    }

}
