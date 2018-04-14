package com.pilambda.findmerenew.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pilambda.findmerenew.Actividades.MapsActivity;
import com.pilambda.findmerenew.Actividades.MenuActivity;
import com.pilambda.findmerenew.Model.MyCoordinates;
import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.MyConstants.UtilFunctions;
import com.pilambda.findmerenew.MyNotificationReceiver;
import com.pilambda.findmerenew.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qualtop on 25/02/18.
 */

public class MyBackGroundService extends Service{

    private MyNotificationReceiver mMyNotificationReceiver = new MyNotificationReceiver();

    public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.action.SmsReceiver");
        this.registerReceiver(mMyNotificationReceiver, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(this.mMyNotificationReceiver);
    }

    private BroadcastReceiver getReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            createCoolNotification();
            String coordenadasError = intent.getStringExtra(MyConstants.SMS_MESSAGE_RECEIVED);


            String coordenadas = UtilFunctions.corrigeErrorTransmisionGsm(coordenadasError);
            Log.i(MyConstants.APPNAKME,"hello from Service" + coordenadas);
            MyCoordinates myCoordinates = new MyCoordinates();
            try {
                JSONObject jsonObject = new JSONObject(coordenadas);
                Log.i(MyConstants.APPNAKME,jsonObject.toString());
                myCoordinates.setLatitud(jsonObject.getDouble("lat"));
                myCoordinates.setLongitud(jsonObject.getDouble("lon"));

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    };

    private void createCoolNotification() {
        Intent intent = new Intent(this, MenuActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification n  = new Notification.Builder(this)
                .setContentTitle("Hiii!!!")
                .setContentText("funciona porfavor!!!! jajaja!! :p")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setSound(uri)
                .setAutoCancel(true).build();
        NotificationManager notificationManager =(NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }

}
