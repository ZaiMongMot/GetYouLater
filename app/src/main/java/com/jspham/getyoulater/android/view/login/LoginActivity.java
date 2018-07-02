package com.jspham.getyoulater.android.view.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jspham.getyoulater.android.R;
import com.jspham.getyoulater.android.view.PeopleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;


    @BindView(R.id.email) EditText mEmailView;
    @BindView(R.id.password) EditText mPasswordView;
    @BindView(R.id.login_progress)
    View mProgressView;
    @BindView(R.id.login_form) View mLoginFormView;

    @OnClick(R.id.email_sign_in_button)
    public void onSignInButtonClicked(){
        Toast.makeText(LoginActivity.this, "Sign In Clicked!", Toast.LENGTH_SHORT).show();
        mFirebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if (mFirebaseAuth.getCurrentUser() != null){
                        Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, PeopleActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this, "Invalid user information", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mFirebaseAuth = FirebaseAuth.getInstance();

    }
}
