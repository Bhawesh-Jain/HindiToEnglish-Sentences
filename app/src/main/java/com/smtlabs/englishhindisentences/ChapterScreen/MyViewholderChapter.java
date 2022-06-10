package com.smtlabs.englishhindisentences.ChapterScreen;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.smtlabs.englishhindisentences.R;

public class MyViewholderChapter extends RecyclerView.ViewHolder {
    final TextView english_sentence;
    final TextView hindi_sentence;
    final ImageView english_btn;
    final ImageView hindi_btn;
    final CardView row;
    final RelativeLayout chapter_detail_parent;
    final RecyclerView chapter;

    public MyViewholderChapter(@NonNull View itemView) {
        super(itemView);
        english_sentence = itemView.findViewById(R.id.txt_chapter_row_header);
        hindi_sentence = itemView.findViewById(R.id.txt_chapter_row_desc);

        english_btn = itemView.findViewById(R.id.english_play);
        hindi_btn = itemView.findViewById(R.id.hindi_play);

        row = itemView.findViewById(R.id.chapter_row);

        chapter_detail_parent = itemView.findViewById(R.id.chapter_detail_parent);

        chapter = itemView.findViewById(R.id.chapter_recyclerview);
    }
}
