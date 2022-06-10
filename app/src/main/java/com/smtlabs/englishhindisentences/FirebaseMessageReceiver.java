package com.smtlabs.englishhindisentences;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smtlabs.englishhindisentences.ChapterListScreen.ChapterListActivity;


public class FirebaseMessageReceiver extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        sendNotification(message.getNotification().getTitle(), message.getNotification().getBody());
    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    private void sendNotification(String title, String messageBody){

        try{
            Intent intent;

            intent = new Intent(this, ChapterListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent
                    .getActivity(this,
                            0,intent,
                            PendingIntent.FLAG_IMMUTABLE);

            String CHANNEL_ID = "hindi_to_english_channel_01";


            NotificationCompat.Builder mBuidler = new NotificationCompat.Builder(this,CHANNEL_ID);

            Notification notification = mBuidler.setSmallIcon(getNotificationIcon(mBuidler))
                    .setTicker("TICKER_TEXT")
                    .setWhen(0)
                    .setAutoCancel(true)
                    .setSmallIcon(getNotificationIcon(mBuidler))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo))
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setChannelId(CHANNEL_ID)
                    .setContentText(messageBody).build();


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int time = (int) System.currentTimeMillis();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                //the id of the Channel.

                CharSequence name = "Hindi To English";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            notificationManager.notify(time,notification);
        } catch (Exception e){
            e.printStackTrace();
        }


    }
    @SuppressLint("ObsoleteSdkInt")
    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int color = 0x008000;
            notificationBuilder.setColor(color);
            return R.drawable.ic_logo;
        }
        return R.drawable.ic_logo;
    }
}

