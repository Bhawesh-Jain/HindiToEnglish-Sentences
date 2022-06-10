package com.smtlabs.englishhindisentences.ReminderClasses.RoomDatabaseClasses;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReminderDatabaseDao {

    @Query("SELECT * FROM reminderdatamodel")
    List<ReminderDataModel> getAllReminders();

    @Insert
    void insertReminder(ReminderDataModel reminderDataModel);

    @Query("DELETE FROM reminderdatamodel WHERE uid = ( :uid )")
    void deleteById(int uid);

    @Query("UPDATE reminderdatamodel SET date=:date WHERE uid = :id")
    void update(String date, int id);

}
