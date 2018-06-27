/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jspham.getyoulater.android.data.model;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
//@Entity(tableName = "tbl_name")
public class Name implements Serializable {

    @Setter
    @Getter
    @SerializedName("title")
    public String title;

    @Setter
    @Getter
    @SerializedName("first")
    public String firts;

    @Setter
    @Getter
    @SerializedName("last")
    public String last;

    public Name() {
    }
}
