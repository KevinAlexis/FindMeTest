package com.pilambda.findmerenew.Model;

import java.io.Serializable;

/**
 * Created by Alexis on 1/26/2018.
 */

public class Contacto implements Serializable{
    private String mNombre;
    private String mTelefono;
    private String mPhoto;

    public Contacto(){
        mNombre = "";
        mTelefono = "";
        mPhoto = "";
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public String getTelefono() {
        return mTelefono;
    }

    public void setTelefono(String telefono) {
        mTelefono = telefono;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }
}
