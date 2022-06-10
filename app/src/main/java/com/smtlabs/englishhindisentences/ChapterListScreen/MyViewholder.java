package com.smtlabs.englishhindisentences.ChapterListScreen;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smtlabs.englishhindisentences.R;

public class MyViewholder extends RecyclerView.ViewHolder {
    final TextView header;
    final TextView desc;
    final ImageView forward;
    final RelativeLayout row;
    int id;


    public MyViewholder(@NonNull View itemView) {
        super(itemView);
        header = itemView.findViewById(R.id.txt_row_header);
        desc = itemView.findViewById(R.id.txt_row_desc);
        forward = itemView.findViewById(R.id.ic_forward_icon);
        row = itemView.findViewById(R.id.row_main);

    }
}
