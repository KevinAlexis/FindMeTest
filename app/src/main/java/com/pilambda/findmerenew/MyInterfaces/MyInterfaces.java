package com.pilambda.findmerenew.MyInterfaces;

import android.view.View;

/**
 * Created by Alexis on 1/26/2018.
 */

public class MyInterfaces {

    public interface MyOnclickListener{
        void onclick(View view , int position);
    }

    //CODE 0 : UP
    public interface ScroolingDelegate{
        public void scroll(int code);
    }
}
