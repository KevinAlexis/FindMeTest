package com.pilambda.findmerenew.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pilambda.findmerenew.Model.Contacto;
import com.pilambda.findmerenew.R;

/**
 * Created by Alexis on 28/01/18.
 */

public class DialogNuevoContacto extends DialogFragment implements View.OnClickListener{

    private EditText mEditNombre;
    private EditText mEditnumero;
    private EditText mEditCancelar;
    private EditText mEditTextAceptar;
    private Button mButtonAceptar;
    private Button mButtonCancelar;

    private NuevoContactoDelegate delegate;

    public interface NuevoContactoDelegate{
        void dialogAccepted(Contacto nuevoContacto);
        void dialogCancel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_nuevo_contacto,null,false);
        mEditNombre = view.findViewById(R.id.editDialogCapturaNombre);
        mEditnumero = view.findViewById(R.id.editDialogCapturaNumero);
        mButtonAceptar = view.findViewById(R.id.buttonDialogContactoAceptar);
        mButtonCancelar = view.findViewById(R.id.buttonDialogContactoCancelar);
        mButtonCancelar.setOnClickListener(this);
        mButtonAceptar.setOnClickListener(this);
        builder.setView(view);
        AlertDialog alertDialogalertDialog = builder.create();
        return alertDialogalertDialog;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.buttonDialogContactoAceptar:
                Contacto nuevoContacto = new Contacto();
                nuevoContacto.setNombre(mEditNombre.getText().toString());
                nuevoContacto.setTelefono(mEditnumero.getText().toString());
                this.delegate.dialogAccepted(nuevoContacto);
                break;
            case R.id.buttonDialogContactoCancelar:
                delegate.dialogCancel();
                break;
        }
    }

    public void setDelegate(NuevoContactoDelegate delegate) {
        this.delegate = delegate;
    }
}
