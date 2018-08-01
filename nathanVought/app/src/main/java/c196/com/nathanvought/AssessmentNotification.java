package c196.com.nathanvought;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


/**
 * Created by mac on 12/4/17.
 */

public class AssessmentNotification extends BroadcastReceiver {







    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences("Assessment", Context.MODE_PRIVATE);
        //Toast.makeText(context,"Wake up", Toast.LENGTH_LONG).show();
        String name = prefs.getString("Title", "");
        String pw = prefs.getString("Message","");

        Toast.makeText(context,"You have 1 new notification", Toast.LENGTH_LONG).show();



        createNotification(context, name, pw, "Alert");


    }

    public void createNotification(Context context, String msg, String msgText, String msgAlert){

        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(msg)
                        .setTicker(msgAlert)
                        .setContentText(msgText);

        mBuilder.setContentIntent(notificIntent);


        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(3, mBuilder.build());

    }





}
