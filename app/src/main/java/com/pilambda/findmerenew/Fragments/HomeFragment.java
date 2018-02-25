package com.pilambda.findmerenew.Fragments;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pilambda.findmerenew.Actividades.MapsActivity;
import com.pilambda.findmerenew.Actividades.MenuActivity;
import com.pilambda.findmerenew.Actividades.RegistroActivity;
import com.pilambda.findmerenew.Model.MyCoordinates;
import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.R;
import com.pilambda.findmerenew.Services.MyBackGroundService;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 *@author Kevin Alexis
 */
public class HomeFragment extends Fragment {

    private TextView mTextViewTest;
    private CardView mCardView;
    private Button mButtonEmergency;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mTextViewTest = view.findViewById(R.id.textViewNombreContactoCurrent);
        mButtonEmergency = view.findViewById(R.id.button_emergency);
        mButtonEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNotification();
            }
        });
        mCardView = view.findViewById(R.id.cardViewUsuario);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });
        mTextViewTest.setText("Nombre");
        Intent intent = new Intent(getActivity(), MyBackGroundService.class);
        getActivity().startService(intent);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.action.SmsReceiver");
        getActivity().registerReceiver(getReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(getReceiver);
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
                mTextViewTest.setText("latitud:" + myCoordinates.getLatitud() + "longitud" + myCoordinates.getLongitud());
                Intent mapsActivity = new Intent(getActivity(), MapsActivity.class);
                mapsActivity.putExtra(MapsActivity.CORDENADAS_JSON,myCoordinates);
                startActivity(mapsActivity);
                setNotification();
            } catch (JSONException e) {
                e.printStackTrace();
                mTextViewTest.setText("Hubo un Error");
            }
        }
    };

    private void setNotification() {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getActivity(), (int) System.currentTimeMillis(), intent, 0);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification n  = new Notification.Builder(getActivity())
                .setContentTitle("Hiii!!!")
                .setContentText(":p")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setSound(uri)
                .setAutoCancel(true).build();
        NotificationManager notificationManager =(NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
}
