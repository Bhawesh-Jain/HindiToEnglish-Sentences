package com.smtlabs.englishhindisentences.ReminderClasses.RoomDatabaseClasses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReminderDataModel {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "label")
    public String label;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "repeat")
    public String repeat;

    @ColumnInfo(name = "repeatDays")
    public String repeatDays = "";
// Week Days ----------------------------------------------------
    @ColumnInfo(name = "monday")
    public boolean monday = false;

    @ColumnInfo(name = "tuesday")
    public boolean tuesday = false;

    @ColumnInfo(name = "wednesday")
    public boolean wednesday = false;

    @ColumnInfo(name = "thursday")
    public boolean thursday = false;

    @ColumnInfo(name = "friday")
    public boolean friday = false;

    @ColumnInfo(name = "saturday")
    public boolean saturday = false;

    @ColumnInfo(name = "sunday")
    public boolean sunday = false;
// Week Days ----------------------------------------------------


    public ReminderDataModel(String label, String time, String date, String repeat, String repeatDays
            , boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.label = label;
        this.time = time;
        this.date = date;
        this.repeat = repeat;
        this.repeatDays = repeatDays;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public String getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(String repeatDays) {
        this.repeatDays = repeatDays;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }
}
