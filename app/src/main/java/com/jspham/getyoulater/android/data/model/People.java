/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jspham.getyoulater.android.data.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;
import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "tbl_people")
public class People implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer id;

    @SerializedName("gender")
    public String gender;

    @Embedded
    @SerializedName("name")
    public Name name;

    @Embedded
    @SerializedName("location")
    public Location location;
    @Setter
    @Getter
    @SerializedName("email")
    public String mail;

    @Embedded
    @SerializedName("login")
    public Login userName;
    @Setter
    @Getter
    @SerializedName("phone")
    public String phone;

    @SerializedName("cell")
    public String cell;

    @Embedded
    @SerializedName("picture")
    public Picture picture;

    public String fullName;

    public People() {
    }

    public boolean hasEmail() {
        return mail != null && !mail.isEmpty();
    }
}
