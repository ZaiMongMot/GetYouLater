package com.jspham.getyoulater.android.data.local.db;

import com.jspham.getyoulater.android.data.local.db.converter.DateTypeConverter;
import com.jspham.getyoulater.android.data.local.db.dao.ContactDao;
import com.jspham.getyoulater.android.data.local.db.dao.PeopleDao;
import com.jspham.getyoulater.android.data.local.db.dao.UserDao;
import com.jspham.getyoulater.android.data.model.Contact;
import com.jspham.getyoulater.android.data.model.People;
import com.jspham.getyoulater.android.data.model.User;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by thang_phamvan on 25-May-18.
 **/
@Database(entities = {People.class, User.class, Contact.class }, version = 1, exportSchema = false)
@TypeConverters({ DateTypeConverter.class })
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    private static final String DB_NAME = "glt.db";

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }

    public static void destroyInstance(){
        instance = null;
    }

    public abstract PeopleDao peopleDao();
    public abstract ContactDao contactDao();
    public abstract UserDao userDao();
}
