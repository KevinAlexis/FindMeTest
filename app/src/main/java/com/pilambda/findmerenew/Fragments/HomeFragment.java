package com.pilambda.findmerenew.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pilambda.findmerenew.Actividades.MapsActivity;
import com.pilambda.findmerenew.Model.MyCoordinates;
import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *@author Kevin Alexis
 */
public class HomeFragment extends Fragment {

    private TextView mTextViewTest;
    private CardView mCardView;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mTextViewTest = view.findViewById(R.id.textViewNombreContactoCurrent);
        mCardView = view.findViewById(R.id.cardViewUsuario);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });
        mTextViewTest.setText("Nombre");
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
            String coordenadas = intent.getStringExtra(MyConstants.SMS_MESSAGE_RECEIVED);
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
            } catch (JSONException e) {
                e.printStackTrace();
                mTextViewTest.setText("Hubo un Error");
            }
        }
    };
}
