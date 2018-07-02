package com.jspham.getyoulater.android.data.local.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by thang_phamvan on 28-May-18.
 **/
public class DateTypeConverter {
    @TypeConverter
    public long convertDateToLong(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date convertLongToDate(long time){
        return new Date(time);
    }
}
