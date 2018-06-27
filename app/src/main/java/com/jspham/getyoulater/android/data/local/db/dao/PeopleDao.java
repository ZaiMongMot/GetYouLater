package com.jspham.getyoulater.android.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.jspham.getyoulater.android.data.model.People;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by thang_phamvan on 25-May-18.
 **/
@Dao
public interface PeopleDao {
//
    @Delete
    void delete(People people);

    @Query("SELECT * FROM tbl_people WHERE phone LIKE :phone LIMIT 1")
    Flowable<People> findByPhone(String phone);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(People... people);

    @Query("SELECT * FROM tbl_people")
    Flowable<List<People>> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<People> peoples);

}
