package com.pilambda.findmerenew.Model;

import java.io.Serializable;

/**
 * Created by Alexis on 2/10/2018.
 */

public class MyCoordinates implements Serializable{

    private double mLatitud;
    private double mLongitud;

    public double getLatitud() {
        return mLatitud;
    }

    public void setLatitud(double latitud) {
        mLatitud = latitud;
    }

    public double getLongitud() {
        return mLongitud;
    }

    public void setLongitud(double longitud) {
        mLongitud = longitud;
    }
}
