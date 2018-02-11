package com.pilambda.findmerenew.Fragments;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/***
 * Create By @Alexis
 */
public class ContactosFragment extends Fragment implements MyInterfaces.MyOnclickListener,View.OnClickListener,DialogAskPhone.DialogCrearCOnactoDelegate {

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
        mContactos = getAllContacts();
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
        Contacto contactoSeleccionado = mContactos.get(position);
        DialogAskPhone dialogFragment = DialogAskPhone.newInstance(contactoSeleccionado);
        dialogFragment.setDelegate(this);
        //dialogFragment.setOnClickListener(this);
        dialogFragment.show(getFragmentManager(),"");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_dialog_ask_ok:
                Log.i(MyConstants.APPNAKME,"ok button clicked");
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MyConstants.setUserLogged(true,getActivity());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void contactoCreated(Contacto contact) {
        Log.i(MyConstants.APPNAKME,"ok button clicked");
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        MyConstants.setUserLogged(true,getActivity());
        startActivity(intent);
    }

    private void setDummyData(){
        for (int i = 0 ; i < 10 ; i++ ){
            Contacto contacto = new Contacto();
            contacto.setNombre("contacto");
            mContactos.add(contacto);
        }
    }

    private ArrayList<Contacto> getAllContacts() {
        ArrayList<Contacto> contactVOList = new ArrayList();
        Contacto contactVO;
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    contactVO = new Contacto();
                    contactVO.setNombre(name);
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contactVO.setTelefono(phoneNumber);
                    }
                    phoneCursor.close();

                    /*
                    Cursor emailCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }

                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getActivity().getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,Long.valueOf(id)));
                    if (inputStream != null) {
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        contactVO.setPhoto(encodedImage);
                    }
                     */

                    contactVOList.add(contactVO);
                }
            }
        }
        return contactVOList;
    }




}
