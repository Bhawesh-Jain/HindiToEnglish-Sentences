package com.smtlabs.englishhindisentences.ReminderClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smtlabs.englishhindisentences.R;
import com.smtlabs.englishhindisentences.ReminderClasses.RoomDatabaseClasses.ReminderDataModel;

import java.util.List;

public class ReminderRecyclerviewAdapter extends RecyclerView.Adapter<ReminderRecyclerviewAdapter.viewholder> {
    final List<ReminderDataModel> data;
    final Context context;
    final ReminderMethodInterface reminderMethodInterface;
    public ReminderRecyclerviewAdapter(List<ReminderDataModel> data, Context context,
                                       ReminderMethodInterface reminderMethodInterface) {
        this.data = data;
        this.context = context;
        this.reminderMethodInterface = reminderMethodInterface;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow_reminder, parent, false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.label.setText(data.get(position).getLabel());
        holder.date.setText(data.get(position).getDate());
        holder.time.setText(data.get(position).getTime());
        holder.daily.setText(data.get(position).getRepeat());

        holder.repeatDays.setText(data.get(position).getRepeatDays());

        holder.delete.setOnClickListener(view -> {
            reminderMethodInterface.onDeleteClicked(data.get(position).getUid());
            data.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {

        final TextView label;
        final TextView date;
        final TextView time;
        final TextView repeatDays;
        final TextView daily;
        final ImageView delete;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            label = itemView.findViewById(R.id.txt_singlerow_reminder_label);
            daily = itemView.findViewById(R.id.txt_singlerow_reminder_daily);
            date = itemView.findViewById(R.id.txt_singlerow_reminder_date);
            repeatDays = itemView.findViewById(R.id.txt_singlerow_repeat_days);
            time = itemView.findViewById(R.id.txt_singlerow_reminder_time);
            delete = itemView.findViewById(R.id.ic_singlerow_reminder_delete);

        }
    }
}
