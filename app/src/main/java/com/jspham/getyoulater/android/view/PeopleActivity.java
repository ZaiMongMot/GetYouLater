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

package com.jspham.getyoulater.android.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.jspham.getyoulater.android.R;
import com.jspham.getyoulater.android.data.PeopleFactory;
import com.jspham.getyoulater.android.data.model.Name;
import com.jspham.getyoulater.android.data.model.People;
import com.jspham.getyoulater.android.databinding.PeopleActivityBinding;
import com.jspham.getyoulater.android.viewmodel.PeopleViewModel;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class PeopleActivity extends AppCompatActivity implements Observer {

    private PeopleActivityBinding peopleActivityBinding;
    private PeopleViewModel peopleViewModel;
    private static final int PERMISSION_REQUEST_CONTACT = 111;
    private static final int PERMISSION_WRITE_CONTACT = 112;
    private List<People> peopleList;
    private PeopleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);
//
        setSupportActionBar(toolbar);
        setupListPeopleView(peopleActivityBinding.listPeople);
        setupObserver(peopleViewModel);
        peopleViewModel.initializeViews();

//        peopleViewModel.fetchPeopleList();
    }

    private void initDataBinding() {
//        setContentView(R.layout.people_activity);
        peopleActivityBinding = DataBindingUtil.setContentView(this, R.layout.people_activity);
        peopleViewModel = new PeopleViewModel(this);
        peopleActivityBinding.setMainViewModel(peopleViewModel);
    }

    private void setupListPeopleView(RecyclerView listPeople) {
        adapter = new PeopleAdapter();
        listPeople.setAdapter(adapter);
        listPeople.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        peopleViewModel.reset();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_github:
                startActivityActionView();
                return true;
            case R.id.menu_contact:
                // User chose the "Import Contact" action, mark the current item
                // as a favorite...
                requestContactPermission(PERMISSION_REQUEST_CONTACT);
                return true;
            case R.id.menu_friends:
                // User chose the "Friend" action, mark the current item
                // as a favorite...
                requestContactPermission(PERMISSION_WRITE_CONTACT);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void requestContactPermission(int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (type) {
                case PERMISSION_REQUEST_CONTACT:
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.READ_CONTACTS)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Contacts access needed");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setMessage("please confirm Contacts access");//TODO put real question
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    requestPermissions(
                                            new String[]
                                                    {Manifest.permission.READ_CONTACTS}
                                            , PERMISSION_REQUEST_CONTACT);
                                }
                            });
                            builder.show();
                        } else {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.READ_CONTACTS},
                                    PERMISSION_REQUEST_CONTACT);
                        }
                    } else {
                        getContact();
                    }
                    return;
                case PERMISSION_WRITE_CONTACT:{
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.WRITE_CONTACTS)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Contacts access needed to write");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setMessage("please confirm Contacts access");//TODO put real question
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    requestPermissions(
                                            new String[]
                                                    {Manifest.permission.WRITE_CONTACTS}
                                            , PERMISSION_WRITE_CONTACT);
                                }
                            });
                            builder.show();
                        } else {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.WRITE_CONTACTS},
                                    PERMISSION_WRITE_CONTACT);
                        }
                    } else{
                        for (People p : peopleList){
//                            insertPeopleToContactList(p.getEmail(), p.getPhone());
                        }
                    }
                    return;
                }
            }
        }
    }

    private void getContact(){
        List<String> contactList = new ArrayList<String>();
        List<People> peopleList = new ArrayList<>();
        String phoneNumber = null;
        String email = null;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;
        StringBuffer output;
        ContentResolver contentResolver = getContentResolver();
        People people = null;
        final Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        // Iterate every contact in the phone
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                people = new People();
                output = new StringBuffer();
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                people.name = new Name();
                people.name.setFirts(name);
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    output.append("\n First Name:" + name);
                    //This is to read multiple phone numbers associated with the same contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        people.setPhone(phoneNumber);
                        output.append("\n Phone number:" + phoneNumber);
                    }
                    phoneCursor.close();
                    // Read every email id associated with the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        people.setMail(email);
                        output.append("\n Email:" + email);
                    }
                    emailCursor.close();
//                    String columns[] = {
//                            ContactsContract.CommonDataKinds.Event.START_DATE,
//                            ContactsContract.CommonDataKinds.Event.TYPE,
//                            ContactsContract.CommonDataKinds.Event.MIMETYPE,
//                    };
//                    String where = ContactsContract.CommonDataKinds.Event.TYPE + "=" + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY +
//                            " and " + ContactsContract.CommonDataKinds.Event.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' and " + ContactsContract.Data.CONTACT_ID + " = " + contact_id;
//                    String[] selectionArgs = null;
//                    String sortOrder = ContactsContract.Contacts.DISPLAY_NAME;
//                    Cursor birthdayCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, columns, where, selectionArgs, sortOrder);
//                    Log.d("BDAY", birthdayCur.getCount()+"");
//                    if (birthdayCur.getCount() > 0) {
//                        while (birthdayCur.moveToNext()) {
//                            String birthday = birthdayCur.getString(birthdayCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
//                            output.append("Birthday :" + birthday);
//                            Log.d("BDAY", birthday);
//                        }
//                    }
//                    birthdayCur.close();
                }
                // Add the contact to the ArrayList
                peopleList.add(people);
                contactList.add(output.toString());
            }
        } else {
            Toast.makeText(this, "Contact is empty", Toast.LENGTH_SHORT).show();
        }
//        adapter.setPeopleList(peopleList);
        peopleViewModel.changePeopleDataSet(peopleList);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // showRationale = false if user clicks Never Ask Again, otherwise true
                    boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS);
                    if (showRationale) {
                        // do something here to handle degraded mode
                    } else {
                        Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
            case PERMISSION_WRITE_CONTACT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    for (People p : peopleList){
//                        insertPeopleToContactList(p.getEmail(), p.getPhone());
                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // showRationale = false if user clicks Never Ask Again, otherwise true
                    boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS);
                    if (showRationale) {
                        // do something here to handle degraded mode
                    } else {
                        Toast.makeText(this, "Write Contacts permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions,grantResults);
    }

    private void startActivityActionView() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PeopleFactory.PROJECT_URL)));
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof PeopleViewModel) {
            Log.d("PeopleAct", "update: from observable!!!!!!");
            peopleList = new ArrayList<>();
            PeopleAdapter peopleAdapter = (PeopleAdapter) peopleActivityBinding.listPeople.getAdapter();
            PeopleViewModel peopleViewModel = (PeopleViewModel) observable;
            peopleViewModel.showRecyclerViews();
            peopleAdapter.setPeopleList(peopleViewModel.getPeopleList());
            peopleList.addAll(peopleViewModel.getPeopleList());
        }
    }

    // insert people to phone list
    private void insertPeopleToContactList(String displayName, String phoneNumber){
        Uri addContactsUri = ContactsContract.Data.CONTENT_URI;
        // Add an empty contact and get the generated id.
        long rowContactId = getRawContactId();
        // Add contact name data.
        insertContactDisplayName(addContactsUri, rowContactId, displayName);
        // Add contact phone data
        insertContactPhoneNumber(addContactsUri, rowContactId, phoneNumber, "work");
    }

    // This method will only insert an empty data to RawContacts.CONTENT_URI
    // The purpose is to get a system generated raw contact id.
    private long getRawContactId() {
        // Inser an empty contact.
        ContentValues contentValues = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        // Get the newly created contact raw id.
        long ret = ContentUris.parseId(rawContactUri);
        return ret;
    }

    // Insert newly created contact display name.
    private void insertContactDisplayName(Uri addContactsUri, long rawContactId, String displayName) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);

        getContentResolver().insert(addContactsUri, contentValues);

    }

    private void insertContactPhoneNumber(Uri addContactsUri, long rawContactId, String phoneNumber, String phoneTypeStr) {
        // Create a ContentValues object.
        ContentValues contentValues = new ContentValues();

        // Each contact must has an id to avoid java.lang.IllegalArgumentException: raw_contact_id is required error.
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);

        // Put phone number value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);

        // Calculate phone type by user selection.
        int phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;

        if ("home".equalsIgnoreCase(phoneTypeStr)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
        } else if ("mobile".equalsIgnoreCase(phoneTypeStr)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
        } else if ("work".equalsIgnoreCase(phoneTypeStr)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
        }
        // Put phone type value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneContactType);

        // Insert new contact data into phone contact list.
        getContentResolver().insert(addContactsUri, contentValues);

    }

    //end
}
