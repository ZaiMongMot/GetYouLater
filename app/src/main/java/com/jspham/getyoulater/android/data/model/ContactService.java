package com.jspham.getyoulater.android.data.model;

import java.util.List;

import retrofit2.Call;

/**
 * Created by thang_phamvan on 28-May-18.
 **/
public interface ContactService {
    Call<List<Contact>>getContacts();
}
