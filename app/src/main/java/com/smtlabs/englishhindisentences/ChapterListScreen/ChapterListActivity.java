package com.smtlabs.englishhindisentences.ChapterListScreen;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;
import com.smtlabs.englishhindisentences.ChapterScreen.ChapterDetailActivity;
import com.smtlabs.englishhindisentences.MoreAppsActivity;
import com.smtlabs.englishhindisentences.R;
import com.smtlabs.englishhindisentences.ReminderClasses.ReminderActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChapterListActivity extends AppCompatActivity implements RecyclerOnclickInterface{
    RecyclerView rcv;
    ImageView ic_share;
    final ArrayList<String> chapterEnglish = new ArrayList<>();
    final ArrayList<String> chapterHindi = new ArrayList<>();

    DrawerLayout drawerLayout;
    NavigationView nav;

    RelativeLayout flirt, setting, lekha, manager;

    private InterstitialAd mInterstitialAd;


    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {            //Nice
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        new Handler().postDelayed(() -> {
            MobileAds.initialize(getApplicationContext(), initializationStatus -> {});
            setAds();
        }, 100);

        nav = findViewById(R.id.nav_menu);
        drawerLayout = findViewById(R.id.drawer_chapter_list_activity);

        ImageView menu = findViewById(R.id.ic_menu);
        menu.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.menu_more:
                    startActivity(new Intent(ChapterListActivity.this, MoreAppsActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.menu_privacy:
                    Toast.makeText(getApplicationContext(), "Privacy Policy option Clicked", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.menu_rating:
                    Toast.makeText(getApplicationContext(), "Rate App option Clicked", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.menu_reminder:
                    startActivity(new Intent(ChapterListActivity.this, ReminderActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });

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


        ic_share = findViewById(R.id.ic_share);
        ic_share.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody = "Your body here";
            String shareSub = "Your Link here";
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
            intent.putExtra(Intent.EXTRA_TEXT,shareSub);
            startActivity(Intent.createChooser(intent, "Share using"));
        });

        rcv = findViewById(R.id.main_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcv.setLayoutManager(linearLayoutManager);

        try {
            JSONObject jsonObject = new JSONObject(jsonObjectFromAssets());
            JSONArray jsonArray = jsonObject.getJSONArray("chapters");
            for (int i = 0; i<jsonArray.length(); i++){
                JSONObject userdata = jsonArray.getJSONObject(i);
                chapterEnglish.add(userdata.getString("english"));
                chapterHindi.add(userdata.getString("hindi"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyAdapter adapter = new MyAdapter(chapterEnglish, chapterHindi, ChapterListActivity.this, this);
        rcv.setAdapter(adapter);
    }


    @Nullable
    private String jsonObjectFromAssets() {
        String json;
        try {
            InputStream inputStream = getAssets().open("content.json");
            int fileSize = inputStream.available();
            byte[] bufferData = new byte[fileSize];
            inputStream.read(bufferData);
            inputStream.close();
            json = new String(bufferData, StandardCharsets.UTF_8);

        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onChapterClicked(String title) {
        if (mInterstitialAd != null){
            mInterstitialAd.show(ChapterListActivity.this);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    Intent i = new Intent(ChapterListActivity.this, ChapterDetailActivity.class);
                    i.putExtra("Title", title);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(i);

                    new Handler().postDelayed(() -> loadInterstitialAd(getApplicationContext()), 5000);
                }
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    Intent i = new Intent(ChapterListActivity.this, ChapterDetailActivity.class);
                    i.putExtra("Title", title);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(i);
                }
            });


        }
        else {
            Intent i = new Intent(ChapterListActivity.this, ChapterDetailActivity.class);
            i.putExtra("Title", title);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
        }
    }

    public void loadInterstitialAd(Context context){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, getString(R.string.interstitial_ad_unit_id), adRequest,interstitialCallback);
    }

    InterstitialAdLoadCallback interstitialCallback = new InterstitialAdLoadCallback(){
        @Override
        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
            mInterstitialAd = interstitialAd;
        }

        @Override
        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
            mInterstitialAd = null;
        }
    };

    private void setAds() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.interstitial_ad_unit_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

}

//        FloatingActionButton home = findViewById(R.id.fab_home_chapter_list);
//        home.setOnClickListener(view -> {
//            Intent o = new Intent(ChapterListActivity.this, DashboardActivity.class);
//            o.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(o);
//        });


