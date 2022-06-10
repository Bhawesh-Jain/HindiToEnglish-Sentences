package com.smtlabs.englishhindisentences.ChapterScreen;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smtlabs.englishhindisentences.R;
import com.smtlabs.englishhindisentences.databinding.ActivityChapterDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class ChapterDetailActivity extends AppCompatActivity implements SpeakingInterface{
    ImageView back;
    RecyclerView chapter_rcv;

    ArrayList<String> sentenceEnglish = new ArrayList<>();
    ArrayList<String> sentenceHindi = new ArrayList<>();

    TextToSpeech tts_Hindi, tts_English;

    private ActivityChapterDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChapterDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back = findViewById(R.id.ic_back_chapter);
        back.setOnClickListener(view -> finish());


        tts_English = new TextToSpeech(this, i -> {
            if (i != TextToSpeech.ERROR) {
                tts_English.setLanguage(Locale.ENGLISH);
                tts_English.setSpeechRate(.7f);
            }
            else {
                Log.e(TAG, "Error in Initialization of TTS English");
            }
        });

        tts_Hindi = new TextToSpeech(this, i -> {
            if (i != TextToSpeech.ERROR) {
                tts_Hindi.setLanguage(new Locale("hin"));
            }
            else {
                Log.e(TAG, "Error in Initialization of TTS English");
            }
        });

        String title = getIntent().getStringExtra("Title");

        binding.txtChapterTitle.setText(title);

        chapter_rcv = findViewById(R.id.chapter_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chapter_rcv.setLayoutManager(linearLayoutManager);

        MyAdapterChapter adapterChapter = new MyAdapterChapter(sentenceEnglish, sentenceHindi, getApplicationContext(), this);
        chapter_rcv.setAdapter(adapterChapter);

        getData(title);

    }

    private void getData(String title){
        try {
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(jsonObjectFromAssets()));
            JSONArray jsonArray = jsonObject.getJSONArray("chapters");
            for (int i = 0; i<jsonArray.length(); i++){
                JSONObject userdata = jsonArray.getJSONObject(i);
                String comp = userdata.getString("english");
                if (title.equals(comp)){
                    JSONArray sentences = userdata.getJSONArray("sentenses");
                    for (int j = 0; j<sentences.length(); j++) {
                        JSONObject sentence = sentences.getJSONObject(j);
                        sentenceEnglish.add(sentence.getString("english"));
                        sentenceHindi.add(sentence.getString("hindi"));
                    }
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String jsonObjectFromAssets() {
        String json;
        try {
            InputStream inputStream = getAssets().open("content.json");
            int fileSize = inputStream.available();
            byte[] bufferdata = new byte[fileSize];
            inputStream.read(bufferdata);
            inputStream.close();
            json = new String(bufferdata, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }


    private boolean isPlaying = false;

    @Override
    public void OnClickSpeakEnglish(String Text, ImageView id) {
        if (!isPlaying) {
            isPlaying = true;
            tts_English.speak(Text, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
            tts_English.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String s) {
                    id.setImageResource(R.drawable.ic_speaker_pause);
                }

                @Override
                public void onDone(String s) {
                    id.setImageResource(R.drawable.ic_speaker_play);
                    isPlaying = false;
                }

                @Override
                public void onError(String s) {
                    id.setImageResource(R.drawable.ic_speaker_play);
                }

            });
        }
        else {
            Toast.makeText(this, "Audio Already Playing. Please Wait.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnClickSpeakHindi(String Text, ImageView id) {
        if (!isPlaying) {
            isPlaying = true;
            tts_Hindi.speak(Text, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
            tts_Hindi.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String s) {
                    id.setImageResource(R.drawable.ic_speaker_pause);
                }

                @Override
                public void onDone(String s) {
                    id.setImageResource(R.drawable.ic_speaker_play);
                    isPlaying =false;
                }

                @Override
                public void onError(String s) {
                    id.setImageResource(R.drawable.ic_speaker_play);
                }
            });
        }
        else {
            Toast.makeText(this, "Audio Already Playing. Please Wait.", Toast.LENGTH_SHORT).show();
        }
    }
}