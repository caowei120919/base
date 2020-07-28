package com.datacvg.sempmobile.baseandroid.room;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * FileName: RoomDataBase
 * Author: 曹伟
 * Date: 2019/9/16 15:22
 * Description:
 */

@Database(entities = {User.class, ThreadInfo.class, DebugInfo.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class RoomDataBase extends RoomDatabase {

    private static RoomDataBase INSTANCE;

    public abstract UserDao userDao();

    public abstract DebugInfoDao debugInfoDao();

    public abstract ThreadInfoDao threadInfoDao();

    private static final Object sLock = new Object();

    /**
     * Migrate from:
     * version 2 - using Room
     * to
     * version 3 - using Room where the {@link User} has an extra field: date
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Users " + " ADD COLUMN last_update INTEGER");
        }
    };

    /**
     * Migrate from:
     * version 3 - using Room where the {@link User #mId} is an int
     * to
     * version 4 - using Room where the {@link User #mId} is a String
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // SQLite supports a limited operations for ALTER.
            // Changing the type of a column is not directly supported, so this is what we need
            // to do:
            // Create the new table
            database.execSQL("CREATE TABLE users_new (userid TEXT NOT NULL," + "username TEXT," + "last_update INTEGER," + "PRIMARY KEY(userid))");
            // Copy the data
            database.execSQL("INSERT INTO users_new (userid, username, last_update) " + "SELECT userid, username, last_update " + "FROM users");
            // Remove the old table
            database.execSQL("DROP TABLE users");
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE users_new RENAME TO users");
        }
    };

    public static RoomDataBase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RoomDataBase.class, "roombase.db")
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                            }
                        })
                        .build();
            }
            return INSTANCE;
        }
    }

}
