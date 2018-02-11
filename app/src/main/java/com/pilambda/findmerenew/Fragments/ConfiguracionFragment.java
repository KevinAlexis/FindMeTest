package com.pilambda.findmerenew.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfiguracionFragment extends Fragment implements View.OnClickListener{


    private Button mButtonLogOut;
    private SharedPreferences mPreferences;
    public ConfiguracionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);
        mButtonLogOut = view.findViewById(R.id.button_logOut);
        mButtonLogOut.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_logOut:
                Log.i(MyConstants.APPNAKME,"logOutPressed");
                mPreferences = getActivity().getSharedPreferences(MyConstants.PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean(MyConstants.PREF_IS_USER_LOGGED,false);
                editor.commit();
                getActivity().finish();
                break;
        }
    }
}
