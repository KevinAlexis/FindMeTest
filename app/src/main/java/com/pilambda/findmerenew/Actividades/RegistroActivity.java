package com.pilambda.findmerenew.Actividades;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.pilambda.findmerenew.DataBase.DBManager;
import com.pilambda.findmerenew.Dialogs.DialogNuevoContacto;
import com.pilambda.findmerenew.Fragments.ContactosFragment;
import com.pilambda.findmerenew.Model.Contacto;
import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.MyInterfaces.MyInterfaces;
import com.pilambda.findmerenew.R;

/***
 * @author Kevin Alexis
 */
public class RegistroActivity extends AppCompatActivity implements View.OnClickListener, MyInterfaces.ScroolingDelegate, DialogNuevoContacto.NuevoContactoDelegate{


    private FloatingActionButton mButtonAddContact;
    private ContactosFragment contactosFragment;
    private DialogNuevoContacto dialogNuevoContacto;
    private DBManager mDBManager;
    //si la animacion esta activa
    private boolean isAnimationStarted = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mButtonAddContact = findViewById(R.id.button_add_contact);
        mDBManager = DBManager.getsInstance(this);
        mButtonAddContact.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        contactosFragment = new ContactosFragment();
        transaction.replace(R.id.fragment_container_registro, contactosFragment, "myTag");
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        Log.i(MyConstants.APPNAKME, "addContact clicked");
        dialogNuevoContacto = new DialogNuevoContacto();
        dialogNuevoContacto.setDelegate(this);
        dialogNuevoContacto.show(getSupportFragmentManager(), "");
    }

    @Override
    public void scroll(int code) {
        if (code == MyConstants.abajo) {
            contactosFragment = (ContactosFragment) getSupportFragmentManager().findFragmentByTag("myTag");
            Animation animation = new TranslateAnimation(0, 0, 0, 500);
            animation.setDuration(1000);
            mButtonAddContact.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.i(MyConstants.APPNAKME,"onAnim Start");
                    if (isAnimationStarted){
                        mButtonAddContact.setAnimation(null);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.i(MyConstants.APPNAKME,"onAnim End");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mButtonAddContact.setVisibility(View.INVISIBLE);

        } else {
            Animation animation = new TranslateAnimation(0, 0, 500, 0);
            animation.setDuration(800);
            animation.setFillAfter(true);
            mButtonAddContact.startAnimation(animation);

            mButtonAddContact.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dialogAccepted(Contacto nuevoContacto) {
        Log.i(MyConstants.APPNAKME,nuevoContacto.getNombre());
        mDBManager.postNewContacto(nuevoContacto.getNombre());
    }

    @Override
    public void dialogCancel() {
        dialogNuevoContacto.dismiss();
    }

}
