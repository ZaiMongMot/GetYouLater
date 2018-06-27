package com.jspham.getyoulater.android.view.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.jspham.getyoulater.android.R;
import com.jspham.getyoulater.android.view.PeopleActivity;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, PeopleActivity.class));
        }
    }
}
