package com.pilambda.findmerenew.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pilambda.findmerenew.Model.Contacto;
import com.pilambda.findmerenew.R;

/**
 * @author Kevin Alexis
 */

public class DialogAskPhone extends DialogFragment implements View.OnClickListener {

    private Button mButtonOk;
    private TextView mTextNombreContacto;
    private TextView mTextConfirmaNumero;
    private EditText mEditNumeroContacto;
    public static final String CONTACTO_NAME = "cotactoName";
    private View.OnClickListener mOnClickListener;
    private String mNumeroContacto;
    private final int ESTADO_CONTACTO = 0;
    private final int ESTADO_CONTACTO_CONFIRMADO = 1;
    private int mEsatdo = ESTADO_CONTACTO;
    private Contacto mContacto;
    DialogCrearCOnactoDelegate mDelegate;

    public interface DialogCrearCOnactoDelegate{
        void contactoCreated(Contacto contact);
    }
    public static DialogAskPhone newInstance(Contacto contacto) {
        Bundle args = new Bundle();
        DialogAskPhone fragment = new DialogAskPhone();
        args.putSerializable(CONTACTO_NAME,contacto);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Drawable fondoTransparente = new ColorDrawable(Color.TRANSPARENT);
        getDialog().getWindow().setBackgroundDrawable(fondoTransparente);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_ask_phone,null);
        mTextConfirmaNumero = view.findViewById(R.id.textDialogConfirmarNumero);
        mEditNumeroContacto = view.findViewById(R.id.editDialogNumeroConfirmar);
        mButtonOk = view.findViewById(R.id.button_dialog_ask_ok);
        mTextNombreContacto = view.findViewById(R.id.textDialogContactoName);
        mContacto= (Contacto) getArguments().getSerializable(CONTACTO_NAME);
        mTextNombreContacto.setText(mContacto.getNombre());
        mButtonOk.setOnClickListener(this);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setDelegate(DialogCrearCOnactoDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_dialog_ask_ok:
                if (mEsatdo == ESTADO_CONTACTO){
                    boolean isEmpty = mTextNombreContacto.getText().toString().equals("");
                    if (!isEmpty){
                        mNumeroContacto = mEditNumeroContacto.getText().toString();
                        mEsatdo = ESTADO_CONTACTO_CONFIRMADO;
                        mEditNumeroContacto.setText("");
                        mTextConfirmaNumero.setText("Por favor confirma el nuemero de");
                    }
                }else if (mEsatdo == ESTADO_CONTACTO_CONFIRMADO){
                    String numeroConfirmado = mEditNumeroContacto.getText().toString();
                    if (mNumeroContacto.equals(numeroConfirmado)){
                        Contacto contacto = new Contacto();
                        contacto.setTelefono(mEditNumeroContacto.getText().toString());
                        mDelegate.contactoCreated(contacto);
                    }
                    else{
                        mTextConfirmaNumero.setText("El número no coincide, porfavor inténtalo de nuevo");
                        mEditNumeroContacto.setText("");
                        mEsatdo = ESTADO_CONTACTO;
                    }
                }
                break;
        }
    }
}
