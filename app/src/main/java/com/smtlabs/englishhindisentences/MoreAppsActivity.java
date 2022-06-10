package com.smtlabs.englishhindisentences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MoreAppsActivity extends AppCompatActivity {
    RelativeLayout flirt, setting, lekha, manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_apps);

        ImageView back = findViewById(R.id.ic_back_more);
        back.setOnClickListener(view -> finish());

        flirt = findViewById(R.id.flirttime);
        flirt.setOnClickListener(v_arg -> {
            try {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://play.google.com/store/apps/details?id=com.flirttime"));
                startActivity(viewIntent);
            }catch(Exception e) {
                Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    setting = findViewById(R.id.secretsetting);
    setting.setOnClickListener(v_arg -> {
        try {
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.smtgroup.lte4g3gnetworkandsecretsettings"));
            startActivity(viewIntent);
        }catch(Exception e) {
            Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    });


    lekha = findViewById(R.id.lekhajokha);
    lekha.setOnClickListener(v_arg -> {
        try {
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.lekhajokha.udharkhata"));
            startActivity(viewIntent);
        }catch(Exception e) {
            Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    });


    manager = findViewById(R.id.expensemanager);
    manager.setOnClickListener(v_arg -> {
        try {
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.lekhajokha.expensemanager"));
            startActivity(viewIntent);
        }
        catch(Exception e) {
            Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        });
    }
}