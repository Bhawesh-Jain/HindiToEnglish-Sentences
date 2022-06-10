package com.smtlabs.englishhindisentences.ReminderClasses;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.smtlabs.englishhindisentences.R;

import java.util.Objects;

public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "onReceive: AlarmBroadcast onReceive");
        Bundle bundle = intent.getBundleExtra("data");
        String text = bundle.getString("label");
        String repeat = bundle.getString("repeat");
        int id = bundle.getInt("id");
        String date = bundle.getString("time") + "  " + bundle.getString("date");

        intent.removeExtra("label");
        intent.removeExtra("date");
        intent.removeExtra("repeat");
        intent.removeExtra("time");

        //Click on Notification
        Intent intent1 = new Intent(context, ReminderActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.i(TAG, "onReceive: RUNNING "+ repeat);

        if (Objects.equals(repeat, "Once"))
        {
            Log.i(TAG, "onReceive: RUNNING IF ONCE "+ repeat);
            intent1.putExtra("IdToRemove",id);
            intent1.putExtra("REQUEST_CODE", 700);
        }

        //Notification Builder
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001");


        //here we set all the properties for the notification
//        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
//        contentView.setTextViewText(R.id.message, text);
//        contentView.setTextViewText(R.id.date, date);
        mBuilder.setAutoCancel(true);
        mBuilder.setOngoing(true);
        mBuilder.setSmallIcon(R.mipmap.ic_logo);
        mBuilder.setContentText(date);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentTitle(text);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setOnlyAlertOnce(true);
        mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
//        mBuilder.setContent(contentView);
        mBuilder.setContentIntent(pendingIntent);

        //we have to create notification channel after api level 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Notification notification = mBuilder.build();
//        .setSmallIcon(getNotificationIcon(mBuilder)).
        notificationManager.notify(1, notification);

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