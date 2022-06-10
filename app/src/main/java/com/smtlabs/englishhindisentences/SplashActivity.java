package com.smtlabs.englishhindisentences;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.messaging.FirebaseMessaging;
import com.smtlabs.englishhindisentences.ChapterListScreen.ChapterListActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView text = findViewById(R.id.splash_text);


        sharedPreferences = getSharedPreferences("SmtHindiToEnglish", Context.MODE_PRIVATE);


        String first = sharedPreferences.getString("first", "");

        if (!first.equals("")){
            text.setText(" ");
        }

        sharedPreferences.edit().putString("first", "true").apply();

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    Log.i(TAG, "onComplete: TOKEN- "+token);

                });

        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d(TAG, msg);
                });

        int secondsDelayed = 2;
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this,
                    ChapterListActivity.class));
            finish();
        }, secondsDelayed * 1000);
    }

}