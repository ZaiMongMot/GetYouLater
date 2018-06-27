package com.jspham.getyoulater.android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.jspham.getyoulater.android.data.model.Contact;
import com.jspham.getyoulater.android.data.model.ContactResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by thang_phamvan on 28-May-18.
 **/
public class ContactViewModel extends ViewModel {

    private MediatorLiveData<List<Contact>> listContacts;

    private ContactResponse contactRepo;

    @Inject
    public ContactViewModel(ContactResponse response) {
        this.contactRepo = response;
        listContacts = new MediatorLiveData<>();
        listContacts.setValue(null);
        LiveData<List<Contact>> contacts = new ContactResponse().getContact();
        listContacts.addSource(contacts, listContacts::setValue);

    }

    public void init() {
        if (this.listContacts != null) {
            return;
        }
    }

    public LiveData<List<Contact>> getContacts() {
        return new ContactResponse().getContact();
    }
}
