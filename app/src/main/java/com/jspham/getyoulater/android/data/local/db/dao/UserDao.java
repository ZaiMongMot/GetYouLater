package com.jspham.getyoulater.android.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.jspham.getyoulater.android.data.model.User;

import java.util.List;

/**
 * Created by thang_phamvan on 29-May-18.
 **/
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE first_name LIKE :firstname AND last_name LIKE :lastname")
    User findByName(String firstname, String lastname);

    @Query("SELECT COUNT (*) FROM user")
    int countUser();

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

}
