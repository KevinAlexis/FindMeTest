package com.pilambda.findmerenew.Fragments;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pilambda.findmerenew.Actividades.MapsActivity;
import com.pilambda.findmerenew.Actividades.MenuActivity;
import com.pilambda.findmerenew.Actividades.RegistroActivity;
import com.pilambda.findmerenew.Model.MyCoordinates;
import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.MyConstants.UtilFunctions;
import com.pilambda.findmerenew.R;
import com.pilambda.findmerenew.Services.MyBackGroundService;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pilambda.findmerenew.Actividades.MapsActivity.CORDENADAS_JSON;

/**
 * @author Kevin Alexis
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView mTextViewTest;
    private CardView mCardView;
    private Button mButtonEmergency;
    private boolean isButtonChanged = false;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mTextViewTest = view.findViewById(R.id.textViewNombreContactoCurrent);
        mButtonEmergency = view.findViewById(R.id.button_emergency);
        mButtonEmergency.setOnClickListener(this);
        mCardView = view.findViewById(R.id.cardViewUsuario);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        mTextViewTest.setText("Nombre");
        //Iniciando el proceso en backgroun, jaja fue maás fácil de lo que creí
        Intent intent = new Intent(getActivity(), MyBackGroundService.class);
        getActivity().startService(intent);
        //JAJAJAJ soy bien chingón
        if (getActivity().getIntent().hasExtra(CORDENADAS_JSON)) {
            MyCoordinates myCoordinates = (MyCoordinates) getActivity().getIntent().getSerializableExtra(CORDENADAS_JSON);
            Intent mapsActivity = new Intent(getActivity(), MapsActivity.class);
            mapsActivity.putExtra(CORDENADAS_JSON, myCoordinates);
            startActivity(mapsActivity);
        }


        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_emergency:
                final String body = "SOS";
                final String number = "5564144780";
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, body, null, null);
                isButtonChanged = !isButtonChanged;
                Log.i(MyConstants.APPNAKME, "SMS enviado PS: I`m awesome anyway :)");
                Toast.makeText(getActivity(), "Mensaje enviado", Toast.LENGTH_SHORT).show();
                changeButtonColor();
                break;
        }
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
            //String coordenadas = coordenadasError.substring(3,coordenadasError.length());
            String coordenadas = UtilFunctions.corrigeErrorTransmisionGsm(coordenadasError);
            Log.i(MyConstants.APPNAKME, "hello from fragment" + coordenadas);
            MyCoordinates myCoordinates = new MyCoordinates();
            try {
                JSONObject jsonObject = new JSONObject(coordenadas);
                Log.i(MyConstants.APPNAKME, jsonObject.toString());
                myCoordinates.setLatitud(jsonObject.getDouble("lat"));
                myCoordinates.setLongitud(jsonObject.getDouble("lon"));
                mTextViewTest.setText("latitud:" + myCoordinates.getLatitud() + "longitud" + myCoordinates.getLongitud());
                Intent mapsActivity = new Intent(getActivity(), MapsActivity.class);
                mapsActivity.putExtra(CORDENADAS_JSON, myCoordinates);
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
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification n = new Notification.Builder(getActivity())
                .setContentTitle("Hiii!!!")
                .setContentText(":p")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setSound(uri)
                .setAutoCancel(true).build();
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

    public void changeButtonColor() {
        if (isButtonChanged) {
            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.view_border_rounded);
            drawable = DrawableCompat.wrap(drawable);
            int color = getActivity().getResources().getColor(R.color.colorYellow);
            DrawableCompat.setTint(drawable, color);
            mButtonEmergency.setBackground(drawable);
            mButtonEmergency.setTextColor(color);
        } else {
            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.view_border_rounded);
            drawable = DrawableCompat.wrap(drawable);
            int color = getActivity().getResources().getColor(R.color.colorAccent);
            DrawableCompat.setTint(drawable, color);
            mButtonEmergency.setBackground(drawable);
            mButtonEmergency.setTextColor(color);

        }
    }

    public static Drawable setTint(Drawable drawable, int color) {
        final Drawable newDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(newDrawable, color);
        return newDrawable;
    }

}
