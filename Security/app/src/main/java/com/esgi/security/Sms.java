package com.esgi.security;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class Sms extends BroadcastReceiver {

    public static final String SMS_EXTRA_NAME = "pdus";

    public static final int MESSAGE_TYPE_INBOX = 1;
    public static final int MESSAGE_TYPE_SENT = 2;
    public static final int MESSAGE_IS_NOT_READ = 0;
    public static final int MESSAGE_IS_READ = 1;
    public static final int MESSAGE_IS_NOT_SEEN = 0;
    public static final int MESSAGE_IS_SEEN = 1;

    public static final String ADDRESS = "address";
    public static final String DATE = "date";
    public static final String READ = "read";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String BODY = "body";
    public static final String SEEN = "seen";

    @Override
    public void onReceive(Context context, Intent intent) {
        String messageNum = " a été modifié.";
        String modifNum = "4";

        try {

            final Bundle bundle = intent.getExtras();

            if(bundle != null){
                final Object[] extraSms = (Object[])bundle.get(SMS_EXTRA_NAME);
                for (int i = 0; i < extraSms.length;i++){
                    // PDU : format pour sms
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) extraSms[i]);

                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Intent newintent = new Intent(context, MainActivity.class);
                    newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    newintent.putExtra("number", "Numéro de téléphone : " + phoneNumber);
                    newintent.putExtra("message","Message : " + message + " " + messageNum);
                    context.startActivity(newintent);

                    Toast toast = Toast.makeText(context, "Numéro de téléphone: " + phoneNumber + ", message: " + message, Toast.LENGTH_LONG);
                    toast.show();
                    SmsToInbox(context.getContentResolver(), currentMessage);
                }

            }

        }catch (Exception e){
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }
    }

    private void SmsToInbox( ContentResolver contentResolver, SmsMessage currentMessage )
    {
        String save = "Modification";

        ContentValues values = new ContentValues();
        values.put( ADDRESS, currentMessage.getOriginatingAddress() );
        values.put( DATE, currentMessage.getTimestampMillis() );
        values.put( READ, MESSAGE_IS_NOT_READ );
        values.put( STATUS, currentMessage.getStatus() );
        values.put( TYPE, MESSAGE_TYPE_INBOX );
        values.put( SEEN, MESSAGE_IS_NOT_SEEN );

        try
        {
            String newMessage = currentMessage.getMessageBody().toString() ;
            values.put(BODY, newMessage + save);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        contentResolver.insert( Uri.parse("content://sms/inbox"), values );
        Log.e("Sms", "num: " + values);
    }
}
