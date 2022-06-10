package com.smtlabs.englishhindisentences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.smtlabs.englishhindisentences.ChapterListScreen.ChapterListActivity;
import com.smtlabs.englishhindisentences.ReminderClasses.ReminderActivity;
import com.smtlabs.englishhindisentences.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.icMenuDashboard.setOnClickListener(view -> binding.dashboardParentDrawer.openDrawer(GravityCompat.START));

        binding.navMenuDashboard.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.menu_more:
                    startActivity(new Intent(DashboardActivity.this, MoreAppsActivity.class));
                    binding.dashboardParentDrawer.closeDrawer(GravityCompat.START);
                    break;
                case R.id.menu_privacy:
                    Toast.makeText(getApplicationContext(), "Privacy Policy option Clicked", Toast.LENGTH_SHORT).show();
                    binding.dashboardParentDrawer.closeDrawer(GravityCompat.START);
                    break;
                case R.id.menu_rating:
                    Toast.makeText(getApplicationContext(), "Rate App option Clicked", Toast.LENGTH_SHORT).show();
                    binding.dashboardParentDrawer.closeDrawer(GravityCompat.START);
                    break;
                case R.id.menu_reminder:
                    startActivity(new Intent(DashboardActivity.this, ReminderActivity.class));
                    binding.dashboardParentDrawer.closeDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });

        binding.flirttime.setOnClickListener(v_arg -> {
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

        binding.secretsetting.setOnClickListener(v_arg -> {
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


        binding.lekhajokha.setOnClickListener(v_arg -> {
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


        binding.expensemanager.setOnClickListener(v_arg -> {
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

        binding.dashboardChapterList.setOnClickListener(view -> startActivity(new Intent(DashboardActivity.this, ChapterListActivity.class)));

        binding.icShareDashboard.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody = "Your body here";
            String shareSub = "Your Link here";
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
            intent.putExtra(Intent.EXTRA_TEXT,shareSub);
            startActivity(Intent.createChooser(intent, "Share using"));
        });

    }
}