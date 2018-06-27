package com.jspham.getyoulater.android.data.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.jspham.getyoulater.android.data.local.db.dao.ContactDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thang_phamvan on 28-May-18.
 **/
@Singleton
public class ContactResponse {

    private static final String TAG = ContactService.class.getSimpleName();

    private ContactService contactService;
    private ContactDao contactDao;

    @Inject
    public ContactResponse(){

    }

    private void refreshContact(){
    }

    public LiveData<List<Contact>> getContact() {
        final MutableLiveData<List<Contact>> data = new MutableLiveData<>();
        contactService.getContacts().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }
}
