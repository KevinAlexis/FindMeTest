package com.pilambda.findmerenew.MyConstants;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qualtop on 11/03/18.
 */

public class UtilFunctions {

    /**
     * Metodo para corregir los caracteres random que provienen del
     * mensaje de texto bla, bla , bla ......
     * @param coordenadasGsm
     * @return
     */
    public static String corrigeErrorTransmisionGsm(String coordenadasGsm){
        Pattern pattern = Pattern.compile("\\{.*?\\}");
        Matcher matcher = pattern.matcher(coordenadasGsm);
        if(matcher.find()){
            String coordenadasCoorregidas = matcher.group().subSequence(1, matcher.group().length()-1).toString();
            return "{"+coordenadasCoorregidas+"}";
        }else{
            Log.i(MyConstants.APPNAKME,"No encontrado");
        }
        return "no encontrado";
    }

}
