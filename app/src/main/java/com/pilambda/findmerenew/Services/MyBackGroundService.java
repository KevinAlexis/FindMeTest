package com.pilambda.findmerenew.Services;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pilambda.findmerenew.Actividades.MapsActivity;
import com.pilambda.findmerenew.Model.MyCoordinates;
import com.pilambda.findmerenew.MyConstants.MyConstants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qualtop on 25/02/18.
 */

public class MyBackGroundService extends Service{

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private BroadcastReceiver getReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String coordenadasError = intent.getStringExtra(MyConstants.SMS_MESSAGE_RECEIVED);
            String coordenadas = coordenadasError.substring(3,coordenadasError.length());
            Log.i(MyConstants.APPNAKME,"hello from fragment" + coordenadas);
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

}
