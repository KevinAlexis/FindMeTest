package com.pilambda.findmerenew;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.pilambda.findmerenew.MyConstants.MyConstants;
import com.pilambda.findmerenew.Services.MyBackGroundService;

/**
 * Created by Alexis on 2/10/2018.
 */

public class MySmsReceiber extends BroadcastReceiver {

    public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public static final String PDU = "pdus";
    final SmsManager mSmsManager = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String permission = Manifest.permission.RECEIVE_SMS;
        int granted = ContextCompat.checkSelfPermission(context,permission);
        if (granted != PackageManager.PERMISSION_GRANTED){
            String[] permissionList = new String[1];
            permissionList[0] = permission;

        }

        if(intent.getAction().equals(SMS_ACTION)){
            SmsMessage[] message;
            String msgAddress;
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get(PDU);
                    message = new SmsMessage[pdus.length];
                    for(int i = 0; i< pdus.length; i++){
                        message[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msgAddress = message[i].getOriginatingAddress();
                        String msgBody = message[i].getMessageBody();
                        Log.i(MyConstants.APPNAKME, msgAddress);
                        Log.i(MyConstants.APPNAKME,msgBody);
                        Log.i(MyConstants.APPNAKME,"Hello SMS");
                        Intent newIntent = new Intent("android.provider.action.SmsReceiver");
                        newIntent.putExtra(MyConstants.SMS_MESSAGE_RECEIVED, msgBody);

                        context.sendBroadcast(newIntent);
                    }
                }catch(Exception e){
                    Log.i("Exception caught",e.getMessage());
                }
            }

        }
    }
}
