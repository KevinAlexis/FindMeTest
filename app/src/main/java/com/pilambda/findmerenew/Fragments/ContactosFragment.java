package com.pilambda.findmerenew.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pilambda.findmerenew.Adapters.ContactosAdapter;
import com.pilambda.findmerenew.Actividades.MenuActivity;
import com.pilambda.findmerenew.Adapters.ContactosAdapter;
import com.pilambda.findmerenew.Dialogs.DialogAskPhone;
import com.pilambda.findmerenew.Model.Contacto;
import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.MyInterfaces.MyInterfaces;
import com.pilambda.findmerenew.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactosFragment extends Fragment implements MyInterfaces.MyOnclickListener,View.OnClickListener {

    private RecyclerView recyclerContactos;
    private ArrayList<Contacto> mContactos;
    private SharedPreferences mPreferences;
    private MyInterfaces.ScroolingDelegate mDelegate;


    public ContactosFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mDelegate = (MyInterfaces.ScroolingDelegate) context;
        }catch (Error e){
            Log.i(MyConstants.APPNAKME,"sda");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactos, container, false);
        mPreferences = getActivity().getSharedPreferences(MyConstants.PREFERENCES, Context.MODE_PRIVATE);
        boolean isUserLogged = mPreferences.getBoolean(MyConstants.PREF_IS_USER_LOGGED,false);
        if (isUserLogged){
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            startActivity(intent);
        }
        mContactos = new ArrayList<>();
        setDummyData();
        recyclerContactos = view.findViewById(R.id.recyclerContactos);
        ContactosAdapter contactosAdapter = new ContactosAdapter(getActivity(),this);
        contactosAdapter.setContactos(mContactos);
        recyclerContactos.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerContactos.setAdapter(contactosAdapter);
        recyclerContactos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.i(MyConstants.APPNAKME,"scrolling");
                Log.i(MyConstants.APPNAKME,"dy: " + dy);
                if (dy > 0){
                    //Esconde Boton cirtcular
                    mDelegate.scroll(MyConstants.abajo);
                }else{
                    //Vuelvelo visible
                    mDelegate.scroll(MyConstants.arriba);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        return view;
    }

    @Override
    public void onclick(View view, int position) {
        Log.i(MyConstants.APPNAKME , "clicked at " + position);
        DialogAskPhone dialogFragment = new DialogAskPhone();
        dialogFragment.setOnClickListener(this);
        dialogFragment.show(getFragmentManager(),"");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_dialog_ask_ok:
                Log.i(MyConstants.APPNAKME,"ok button clicked");
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                MyConstants.setUserLogged(true,getActivity());
                startActivity(intent);
                break;
        }
    }

    private void setDummyData(){
        for (int i = 0 ; i < 10 ; i++ ){
            Contacto contacto = new Contacto();
            contacto.setNombre("contacto");
            mContactos.add(contacto);
        }
    }


}
