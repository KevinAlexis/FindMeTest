package com.pilambda.findmerenew;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.telephony.SmsMessage;
import android.util.Log;

import com.pilambda.findmerenew.Actividades.MapsActivity;
import com.pilambda.findmerenew.Actividades.MenuActivity;
import com.pilambda.findmerenew.Model.MyCoordinates;
import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.MyConstants.UtilFunctions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 14/04/18.
 */

public class MyNotificationReceiver extends BroadcastReceiver {

    public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public static final String PDU = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_ACTION)) {
            SmsMessage[] message;
            String msgAddress;
            try {
                Object[] pdus = (Object[]) intent.getExtras().get(PDU);
                message = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    message[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    msgAddress = message[i].getOriginatingAddress();
                    String msgBody = message[i].getMessageBody();
                    Log.i(MyConstants.APPNAKME, msgAddress);
                    Log.i(MyConstants.APPNAKME, msgBody);
                    Log.i(MyConstants.APPNAKME, "Hello SMS");
                    Intent newIntent = new Intent("android.provider.action.SmsReceiver");
                    newIntent.putExtra(MyConstants.SMS_MESSAGE_RECEIVED, msgBody);

                    String coordenadas = UtilFunctions.corrigeErrorTransmisionGsm(msgBody);
                    MyCoordinates myCoordinates = new MyCoordinates();
                    try {
                        JSONObject jsonObject = new JSONObject(coordenadas);
                        Log.i(MyConstants.APPNAKME, jsonObject.toString());
                        myCoordinates.setLatitud(jsonObject.getDouble("lat"));
                        myCoordinates.setLongitud(jsonObject.getDouble("lon"));
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    Intent mapsAct = new Intent(context, MenuActivity.class);
                    mapsAct.putExtra(MapsActivity.CORDENADAS_JSON, myCoordinates);
                    PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), mapsAct, 0);
                    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Notification n = new Notification.Builder(context)
                            .setContentTitle(msgAddress)
                            .setContentText("funciona porfavor!!!! jajaja!! :p")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pIntent)
                            .setSound(uri)
                            .setAutoCancel(true).build();
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, n);
                    context.sendBroadcast(newIntent);
                }

            } catch (Exception e) {
                Log.i("Exception caught", e.getMessage());
            }
        }
    }


}
