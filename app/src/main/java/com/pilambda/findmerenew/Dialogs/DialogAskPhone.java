package com.pilambda.findmerenew.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pilambda.findmerenew.R;

/**
 * Created by qualtop on 28/01/18.
 */

public class DialogAskPhone extends DialogFragment {

    private Button mButtonOk;
    private View.OnClickListener mOnClickListener;

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
        mButtonOk = view.findViewById(R.id.button_dialog_ask_ok);
        mButtonOk.setOnClickListener(mOnClickListener);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
}
