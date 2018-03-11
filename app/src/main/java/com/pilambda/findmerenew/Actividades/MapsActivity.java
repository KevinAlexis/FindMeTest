package com.pilambda.findmerenew.Actividades;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pilambda.findmerenew.Model.MyCoordinates;
import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.MyConstants.UtilFunctions;
import com.pilambda.findmerenew.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * @author Kevin Alexis
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String CORDENADAS_JSON = "coordenadasJson";
    MyCoordinates myCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (getIntent().hasExtra(CORDENADAS_JSON)){
            myCoordinates = (MyCoordinates) getIntent().getSerializableExtra(CORDENADAS_JSON);
        }

        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.action.SmsReceiver");
        registerReceiver(getReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(getReceiver);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double latitud = 19.436270;
        double longitud = -99.212196;
        LatLng coordenadaPrueba = new LatLng(latitud,longitud);
        //mMap.addMarker(new MarkerOptions().position(coordenadaPrueba).title("Esta es una prueba"));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenadaPrueba,15));
        if (myCoordinates != null){
            LatLng myCurrentLocation = new LatLng(myCoordinates.getLatitud(), myCoordinates.getLongitud());
            mMap.addMarker(new MarkerOptions().position(myCurrentLocation).title("Hola Esime!!!"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLocation,15));

        }
    }

    private BroadcastReceiver getReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String coordenadasError = intent.getStringExtra(MyConstants.SMS_MESSAGE_RECEIVED);
            //String coordenadas = coordenadasError.substring(3,coordenadasError.length());
            String coordenadas = UtilFunctions.corrigeErrorTransmisionGsm(coordenadasError);
            Log.i(MyConstants.APPNAKME,"hello from fragment" + coordenadas);
            MyCoordinates myCoordinates = new MyCoordinates();
            try {
                JSONObject jsonObject = new JSONObject(coordenadas);
                Log.i(MyConstants.APPNAKME,jsonObject.toString());
                myCoordinates.setLatitud(jsonObject.getDouble("lat"));
                myCoordinates.setLongitud(jsonObject.getDouble("lon"));
                notificaNuevoLugar(myCoordinates);

            } catch (JSONException e) {
                e.printStackTrace();
                setErrorMessage();

            }
        }
    };

    public void notificaNuevoLugar(MyCoordinates myCoordinates){
        Toast.makeText(this, "Nuevo lugar agregado", Toast.LENGTH_SHORT).show();
        LatLng myCurrentLocation = new LatLng(myCoordinates.getLatitud(), myCoordinates.getLongitud());
        Date date = new Date();
        mMap.addMarker(new MarkerOptions().position(myCurrentLocation).title(date.toString()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLocation,15));

    }

    public void setErrorMessage(){
        Toast.makeText(this, "Nuevo lugar agregado", Toast.LENGTH_SHORT).show();
    }



}
