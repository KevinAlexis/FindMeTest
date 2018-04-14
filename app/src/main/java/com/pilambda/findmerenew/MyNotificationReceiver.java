package com.pilambda.findmerenew;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.pilambda.findmerenew.Actividades.MenuActivity;
import com.pilambda.findmerenew.Model.MyCoordinates;
import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.MyConstants.UtilFunctions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 14/04/18.
 */

public class MyNotificationReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        createCoolNotification();


        Intent i = new Intent(context, MenuActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), i, 0);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification n  = new Notification.Builder(context)
                .setContentTitle("Hiii!!!")
                .setContentText("funciona porfavor!!!! jajaja!! :p")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setSound(uri)
                .setAutoCancel(true).build();
        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }


    private void createCoolNotification() {

    }
}
