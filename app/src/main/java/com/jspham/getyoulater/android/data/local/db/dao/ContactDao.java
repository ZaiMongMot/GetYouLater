package com.jspham.getyoulater.android.data.local.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jspham.getyoulater.android.data.model.Contact;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by thang_phamvan on 28-May-18.
 **/
@Dao
public interface ContactDao {
    @Insert(onConflict = REPLACE)
    public void insert(Contact... contacts);

    @Update(onConflict = REPLACE)
    public void update(Contact... contacts);

    @Delete
    public void delete(Contact contact);

    @Query("SELECT * FROM tbl_contact")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM tbl_contact WHERE phoneNumber = :number")
    public LiveData<Contact>getContactByPhoneNumber(String number);

}
