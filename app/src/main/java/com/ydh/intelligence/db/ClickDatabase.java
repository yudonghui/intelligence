package com.ydh.intelligence.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ClickEntity.class, HistoryEntity.class, ActionEntity.class}, version = 1, exportSchema = false)
public abstract class ClickDatabase extends RoomDatabase {

    private static final String DB_NAME = "ClickDatabase.db";
    private static volatile ClickDatabase instance;

    public static synchronized ClickDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static ClickDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                ClickDatabase.class,
                DB_NAME).build();
    }

    public abstract ClickDao getClickDao();

    public abstract HistoryDao getHistoryDao();

    public abstract ActionDao getActionDao();

}
