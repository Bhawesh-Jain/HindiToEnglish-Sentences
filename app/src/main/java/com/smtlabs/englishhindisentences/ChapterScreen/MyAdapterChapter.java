package com.smtlabs.englishhindisentences.ChapterScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smtlabs.englishhindisentences.R;

import java.util.ArrayList;

public class MyAdapterChapter extends RecyclerView.Adapter<MyViewholderChapter> {
    final ArrayList<String> sentenceEng;
    final ArrayList<String> sentenceHin;
    final Context context;

    private final SpeakingInterface speakingInterface;

    public MyAdapterChapter(ArrayList<String> sentenceEng, ArrayList<String> sentenceHin,
                            Context context,SpeakingInterface speakingInterface) {
        this.sentenceEng = sentenceEng;
        this.sentenceHin = sentenceHin;
        this.context = context;
        this.speakingInterface = speakingInterface;
    }

    @NonNull
    @Override
    public MyViewholderChapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow_chapter, parent, false);

        return new MyViewholderChapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholderChapter holder, int position) {

        String eng = sentenceEng.get(position);
        String hin = sentenceHin.get(position);

        holder.english_sentence.setText(eng);
        holder.hindi_sentence.setText(hin);

        holder.english_btn.setOnClickListener(view -> speakingInterface.OnClickSpeakEnglish(eng, holder.english_btn));

        holder.hindi_btn.setOnClickListener(view -> speakingInterface.OnClickSpeakHindi(hin, holder.hindi_btn));

    }

    @Override
    public int getItemCount() {
        return sentenceEng.size();
    }
}
