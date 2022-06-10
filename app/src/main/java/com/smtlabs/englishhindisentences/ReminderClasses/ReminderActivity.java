package com.smtlabs.englishhindisentences.ReminderClasses;

import static android.content.ContentValues.TAG;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.smtlabs.englishhindisentences.R;
import com.smtlabs.englishhindisentences.ReminderClasses.RoomDatabaseClasses.ReminderDataModel;
import com.smtlabs.englishhindisentences.ReminderClasses.RoomDatabaseClasses.ReminderDatabase;
import com.smtlabs.englishhindisentences.ReminderClasses.RoomDatabaseClasses.ReminderDatabaseDao;
import com.smtlabs.englishhindisentences.databinding.ActivityReminderBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ReminderActivity extends AppCompatActivity implements ReminderMethodInterface {
    private AlertDialog dialog;

    private ActivityReminderBinding binding;

    PendingIntent pendingIntent;

    List<ReminderDataModel> data;

    ReminderDataModel discard = new ReminderDataModel("Discard","Discard","Discard","Discard","Discard", false, false, false
            , false, false, false, false);

    int count = 0;
    private String timeToNotify;
    boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        data = getReminderData();

        count = data.size();

        if (count==0){
            createReminderPopup();
            setRecyclerView();
        }

        int rq_code = getIntent().getIntExtra("REQUEST_CODE", 100);

        Log.i(TAG, "onCreate: Request Code = "+rq_code);
        if (rq_code == 700){
            Log.d(TAG, "onCreate: From Notification Click");
        }

        binding.icBackReminder.setOnClickListener(view -> finish());

        binding.icAddReminder.setOnClickListener(view -> {
            createReminderPopup();
            setRecyclerView();
        });

        setRecyclerView();

        getLatestAlarm();

        setAlarms(getLatestAlarm());

        setRecyclerView();
    }

    @Override
    public void onDeleteClicked(int id) {
        ReminderDatabase db = Room.databaseBuilder(getApplicationContext(),
                ReminderDatabase.class, "remind").allowMainThreadQueries().build();

        ReminderDatabaseDao dao = db.ReminderDao();

        dao.deleteById(id);

        count = data.size();

        new Handler().postDelayed(() -> {
            setRecyclerView();
            setAlarms(getLatestAlarm());
        }, 100);
    }

    public void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.reminderRecyclerview.setLayoutManager(linearLayoutManager);

        ReminderRecyclerviewAdapter adapter = new ReminderRecyclerviewAdapter(data, getApplicationContext(), this);

        binding.reminderRecyclerview.setAdapter(adapter);

        count = data.size();

        if (count != 0){
            binding.reminderBackground.setImageResource(R.drawable.custom_bg_white);
        }
        else if (count == 0){
            binding.reminderBackground.setImageResource(R.drawable.ic_alarm);
        }

    }

    public List<ReminderDataModel> getReminderData() {
        ReminderDatabase db = Room.databaseBuilder(getApplicationContext(),
                ReminderDatabase.class, "remind").allowMainThreadQueries().build();

        ReminderDatabaseDao dao = db.ReminderDao();

        return dao.getAllReminders();

    }

    public void addReminderData(ReminderDataModel dataModel){
        ReminderDatabase db = Room.databaseBuilder(getApplicationContext(),
                ReminderDatabase.class, "remind").allowMainThreadQueries().build();

        ReminderDatabaseDao dao = db.ReminderDao();

        dao.insertReminder(dataModel);

        setRecyclerView();

        count = data.size();
    }



    // Reminder Popup

    public void createReminderPopup() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View reminderPopupView = getLayoutInflater().inflate(R.layout.reminder_popup, null);

        dialogBuilder.setView(reminderPopupView);
        dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout daySelect = reminderPopupView.findViewById(R.id.reminder_select_day_container);

        EditText label = reminderPopupView.findViewById(R.id.edt_reminder_label);

        CheckBox repeat = reminderPopupView.findViewById(R.id.reminder_daily_checkbox);

        TextView mon = reminderPopupView.findViewById(R.id.monday);
        TextView tue = reminderPopupView.findViewById(R.id.tuesday);
        TextView wed = reminderPopupView.findViewById(R.id.wednesday);
        TextView thu = reminderPopupView.findViewById(R.id.thursday);
        TextView fri = reminderPopupView.findViewById(R.id.friday);
        TextView sat = reminderPopupView.findViewById(R.id.saturday);
        TextView sun = reminderPopupView.findViewById(R.id.sunday);

        repeat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                monday = false;
                tuesday = false;
                wednesday = false;
                thursday = false;
                friday = false;
                saturday = false;
                sunday = false;
                daySelect.setVisibility(View.VISIBLE);
            }
            if (!b){
                mon.setTextColor(getColor(R.color.custom_grey));
                tue.setTextColor(getColor(R.color.custom_grey));
                wed.setTextColor(getColor(R.color.custom_grey));
                thu.setTextColor(getColor(R.color.custom_grey));
                fri.setTextColor(getColor(R.color.custom_grey));
                sat.setTextColor(getColor(R.color.custom_grey));
                sun.setTextColor(getColor(R.color.red));
                monday = false;
                tuesday = false;
                wednesday = false;
                thursday = false;
                friday = false;
                saturday = false;
                sunday = false;
                daySelect.setVisibility(View.GONE);
            }
        });

        mon.setOnClickListener(view -> monday = daySelector(mon, monday));
        tue.setOnClickListener(view -> tuesday = daySelector(tue, tuesday));
        wed.setOnClickListener(view -> wednesday = daySelector(wed, wednesday));
        thu.setOnClickListener(view -> thursday = daySelector(thu, thursday));
        fri.setOnClickListener(view -> friday = daySelector(fri, friday));
        sat.setOnClickListener(view -> saturday = daySelector(sat, saturday));
        sun.setOnClickListener(view -> {if (!sunday){sun.setTextColor(getColor(R.color.custom_green));sunday = true;} else if (sunday){sun.setTextColor(getColor(R.color.red));sunday = false;}});

        Button setTime = reminderPopupView.findViewById(R.id.btn_reminder_set_time);
        setTime.setOnClickListener(view -> selectTime(setTime));

        Button setDate = reminderPopupView.findViewById(R.id.btn_reminder_set_date);
        setDate.setOnClickListener(view -> selectDate(setDate));

        Button cancel = reminderPopupView.findViewById(R.id.btn_reminder_cancel);
        cancel.setOnClickListener(view -> dialog.dismiss());


        Button confirm = reminderPopupView.findViewById(R.id.btn_reminder_confirm);
        confirm.setOnClickListener(view -> {
            String ReminderLabel = label.getText().toString().trim();
            String ReminderRepeat;

            String ReminderTime = setTime.getText().toString().trim();
            String ReminderDate = setDate.getText().toString().trim();
            String rmRepeat = "";

            if (repeat.isChecked()){
                if (monday){ReminderRepeat = "Repeat: ";}
                else if (tuesday){ReminderRepeat = "Repeat: ";}
                else if (wednesday){ReminderRepeat = "Repeat: ";}
                else if (thursday){ReminderRepeat = "Repeat: ";}
                else if (friday){ReminderRepeat = "Repeat: ";}
                else if (saturday){ReminderRepeat = "Repeat: ";}
                else if (sunday){ReminderRepeat = "Repeat: ";}
                else ReminderRepeat = "Once";

            } else {ReminderRepeat = "Once";}

            if (ReminderLabel.equals("")){
                ReminderLabel = "Reminder";
            }

            if (ReminderTime.equals("Select Time")){
                ReminderTime = getCurrentTime();
            }

            if (ReminderDate.equals("Today")){
                ReminderDate = getCurrentDate();
            }

            if (!ReminderRepeat.equals("Once")){
                if (monday){rmRepeat = rmRepeat.concat("Mon, ");}
                if (tuesday){rmRepeat = rmRepeat.concat("Tue, ");}
                if (wednesday){rmRepeat = rmRepeat.concat("Wed, ");}
                if (thursday){rmRepeat = rmRepeat.concat("Thu, ");}
                if (friday){rmRepeat = rmRepeat.concat("Fri, ");}
                if (saturday){rmRepeat = rmRepeat.concat("Sat, ");}
                if (sunday){rmRepeat = rmRepeat.concat("Sun, ");}
                try {
                    rmRepeat = rmRepeat.substring(0, rmRepeat.length() - 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            addReminderData(new ReminderDataModel(ReminderLabel, ReminderTime, ReminderDate, ReminderRepeat, rmRepeat,
                    monday, tuesday, wednesday, thursday, friday, saturday, sunday));
            data = getReminderData();

            if (data!=null){
                try{
                    setAlarms(getLatestAlarm());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            Toast.makeText(ReminderActivity.this, "Reminder Set", Toast.LENGTH_SHORT).show();

            dialog.dismiss();

            setRecyclerView();
        });
    }


    // Reminder Popup



    // Date And Time Methods
    private boolean daySelector(TextView day, boolean con) {
        if (!con){
            day.setTextColor(getColor(R.color.custom_green));
            return true;
        }
        else if (con){
            day.setTextColor(getColor(R.color.custom_grey));
            return false;
        }
        return false;
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day + "-" + (month + 1) + "-" + year;
    }

    private String getUpdatedDate(int DaysToAdd) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dt = (day) + "-" + (month + 1) + "-" + year;  // Start date

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(Objects.requireNonNull(sdf.parse(dt)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, DaysToAdd);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        return sdf1.format(c.getTime());
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        minute = minute+1;
        if (minute == 60){
            minute = 0;
            hour = hour+1;
        }
        Log.i(TAG, "getCurrentTime:HOUR: "+hour+" MINUTE = "+minute);
        if (minute < 9){
            return hour+":0"+minute;
        }
        return hour+":"+minute;
    }

    private void selectTime(Button TimeBtn) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = (timePicker, i, i1) -> {
            if (timePicker.isShown()){
                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);
                if (i1 <= 9){
                    timeToNotify = i+":0"+i1;
                }
                else {
                    timeToNotify = i+":"+i1;
                }
                TimeBtn.setText(timeToNotify);
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, false);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private void selectDate(Button DateBtn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, day1) -> {
            String date = day1 + "-" + (month1 + 1) + "-" + year1;
            DateBtn.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }

    // Date And Time Methods



    // Alarm Methods

    private ReminderDataModel getLatestAlarm() {
        Date baseDate = null;
        ReminderDataModel selectedModel = null;
        Calendar calendar = Calendar.getInstance();

        Log.i(TAG, "getLatestAlarm: Calender - "+calendar.getTime());

        if (data == null){
            return discard;
        }

        for (int i = 0; i < data.size(); i++) {
            Log.i(TAG, "----------------------------LOOPER IN GET LATEST ALARM: Start of Loop No. = "+i+" -------------------------");

            String date = data.get(i).getDate();
            String time = data.get(i).getTime();
            int uid = data.get(i).getUid();
            time = time.replace("PM", "");
            time = time.replace("AM", "");

            String dateAndTime = date + " " + time;

            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy kk:mm");


            Log.i(TAG, "setAlarms: DateAndTime = " + dateAndTime + " at Loop No. = "+ i);

            try {
                Date formattedDate = formatter.parse(dateAndTime);

                if (formattedDate != null) {

                    if (formattedDate.getTime() <= calendar.getTimeInMillis()) {
                        Log.i(TAG, "getLatestAlarm: Time Already Elapsed! Checking The Entry " + formattedDate.getTime() + "vs" + calendar.getTimeInMillis()
                                + " at Loop No. = " + i);

                        if (!data.get(i).getRepeat().equals("Once")) {
                            int[] array = new int[]{9,0,0,0,0,0,0,0};
                            if (data.get(i).isSunday()){array[1] = 1;}
                            if (data.get(i).isMonday()){array[2] = 1;}
                            if (data.get(i).isTuesday()){array[3] = 1;}
                            if (data.get(i).isWednesday()){array[4] = 1;}
                            if (data.get(i).isThursday()){array[5] = 1;}
                            if (data.get(i).isFriday()){array[6] = 1;}
                            if (data.get(i).isSaturday()){array[7] = 1;}

                            int x = calendar.get(Calendar.DAY_OF_WEEK) + 1;

                            int days = 0;

                            while (true){
                                if (x == 8){x=1;}
                                days++;
                                if (array[x] == 1){
                                    break;
                                }
                                x++;
                            }

                            increaseDateForDailyReminder(uid, getUpdatedDate(days));
                            Log.i(TAG, "getLatestAlarm: Daily Reminder Updated!");

                            continue;
                        }

                        onDeleteClicked(uid);
                        Log.i(TAG, "Deleting Alarms With Time Already Elapsed! ID = " + uid + " at Loop No. = " + i);
                        Toast.makeText(this, "Deleting Reminders With Time Already Elapsed!", Toast.LENGTH_SHORT).show();
                        data = getReminderData();
                        setRecyclerView();

                        continue;
                    }


                    if (baseDate == null) {
                        baseDate = formattedDate;
                        selectedModel = data.get(i);
                        Log.i(TAG, "getLatestAlarm: Changed time from " + baseDate + " to " + formattedDate + " at Loop No. = " + i);
                    }

                    if (baseDate.getTime() > formattedDate.getTime()) {
                        baseDate = formattedDate;
                        selectedModel = data.get(i);
                        Log.i(TAG, "getLatestAlarm: Changed time from " + baseDate + " to " + formattedDate + "Loop No. = " + i);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "----------------------------LOOPER IN GET LATEST ALARM: End of Loop No. = "+i+" -------------------------");

        }


        if(selectedModel == null){
            Log.i(TAG, "getLatestAlarm: Selected Model Was Null Returning Discarded Model");
            return discard;
        }
        else {
            Log.i(TAG, "getLatestAlarm: Resulted Alarm is = "+selectedModel.getLabel());
            return selectedModel;
        }
    }

    private void increaseDateForDailyReminder(int uid, String date) {
        ReminderDatabase db = Room.databaseBuilder(getApplicationContext(),
                ReminderDatabase.class, "remind").allowMainThreadQueries().build();

        ReminderDatabaseDao dao = db.ReminderDao();

        dao.update(date, uid);
        Toast.makeText(this, "Daily Reminders Updated!", Toast.LENGTH_SHORT).show();

        data = getReminderData();

        setRecyclerView();
    }

    public void setAlarms(ReminderDataModel model) {
        Log.i(TAG, "setAlarms: Setting Alarm For the " + model.getLabel());

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        String label = model.getLabel();
        String date = model.getDate();
        String time = model.getTime();
        String repeat = model.getRepeat();
        int id = model.getUid();


        Bundle bundle = new Bundle();
        bundle.putString("label", label);
        bundle.putString("time", time);
        bundle.putString("date", date);
        bundle.putString("repeat", repeat);
        bundle.putInt("id", id);
        Intent intent0 = new Intent(getApplicationContext(), AlarmBroadcast.class);
        intent0.putExtra("data", bundle);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext()
                    , id, intent0, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext()
                    , 0, intent0, PendingIntent.FLAG_IMMUTABLE);
        }

        if (label.equals("Discard")) {
            pendingIntent.cancel();
            return;
        }

        time = time.replace("AM", "");
        time = time.replace("PM", "");
        time = time.trim();

        String dateAndTime = date + " " + time;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy kk:mm");
        try {
            Date formattedDate = formatter.parse(dateAndTime);

            if (formattedDate != null) {
                am.set(AlarmManager.RTC_WAKEUP, formattedDate.getTime(), pendingIntent);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "setAlarms: Set for " + date + " at " + time + " with label " + label);
    }
    // Alarm Methods
}
