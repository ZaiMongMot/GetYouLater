package com.jspham.getyoulater.android.data.model;

import java.util.Date;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by thang_phamvan on 28-May-18.
 **/
@Entity(tableName = "tbl_contact")
public class Contact {
    private String firsName;
    private String lastName;
    @NonNull
    @PrimaryKey
    private String phoneNumber;
    private Date createdDate;

    public String getFirsName() {
        return firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
