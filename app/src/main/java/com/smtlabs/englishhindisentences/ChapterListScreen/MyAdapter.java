package com.smtlabs.englishhindisentences.ChapterListScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smtlabs.englishhindisentences.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewholder> {
    final ArrayList<String> header;
    final ArrayList<String> desc;
    final Context context;
    RecyclerOnclickInterface recyclerOnclickInterface;

    public MyAdapter(ArrayList<String> header, ArrayList<String> desc, Context context, RecyclerOnclickInterface recyclerOnclickInterface) {
        this.header = header;
        this.desc = desc;
        this.context = context;
        this.recyclerOnclickInterface = recyclerOnclickInterface;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow_main, parent, false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.header.setText(header.get(position));
        holder.desc.setText(desc.get(position));


        holder.row.setOnClickListener(view -> recyclerOnclickInterface.onChapterClicked(holder.header.getText().toString()));
    }

    @Override
    public int getItemCount() {
        return header.size();
    }
}
