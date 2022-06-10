package com.smtlabs.englishhindisentences.ReminderClasses.RoomDatabaseClasses;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ReminderDataModel.class}, version = 1)

public abstract class ReminderDatabase extends RoomDatabase {
    public abstract ReminderDatabaseDao ReminderDao();
}
