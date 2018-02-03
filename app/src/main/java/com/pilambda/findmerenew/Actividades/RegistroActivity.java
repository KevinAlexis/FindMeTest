package com.pilambda.findmerenew.Actividades;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.pilambda.findmerenew.Dialogs.DialogNuevoContacto;
import com.pilambda.findmerenew.Fragments.ContactosFragment;
import com.pilambda.findmerenew.Model.Contacto;
import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.MyInterfaces.MyInterfaces;
import com.pilambda.findmerenew.R;

import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener, MyInterfaces.ScroolingDelegate {


    private FloatingActionButton mButtonAddContact;
    private ContactosFragment contactosFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mButtonAddContact = findViewById(R.id.button_add_contact);
        mButtonAddContact.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        contactosFragment = new ContactosFragment();
        transaction.replace(R.id.fragment_container_registro, contactosFragment, "myTag");
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        Log.i(MyConstants.APPNAKME, "addContact clicked");
        DialogNuevoContacto dialogNuevoContacto = new DialogNuevoContacto();

        dialogNuevoContacto.show(getSupportFragmentManager(), "");
    }

    @Override
    public void scroll(int code) {
        if (code == MyConstants.abajo) {
            contactosFragment = (ContactosFragment) getSupportFragmentManager().findFragmentByTag("myTag");
            Animation animation = new TranslateAnimation(0, 0, 0, 500);
            animation.setDuration(1000);
            animation.setFillAfter(true);
            mButtonAddContact.startAnimation(animation);
            mButtonAddContact.setVisibility(View.INVISIBLE);

        } else {
            Animation animation = new TranslateAnimation(0, 0, 200, 0);
            animation.setDuration(1000);
            animation.setFillAfter(true);
            mButtonAddContact.startAnimation(animation);
            mButtonAddContact.setVisibility(View.VISIBLE);
        }
    }

    private void agregaNuevoContacto(){
        Contacto contacto = new Contacto();
        contacto.setNombre("Amanda");
    }

}
