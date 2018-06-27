package com.jspham.getyoulater.android.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jspham.getyoulater.android.R;
import com.jspham.getyoulater.android.data.local.db.AppDatabase;
import com.jspham.getyoulater.android.data.local.db.dao.UserDao;
import com.jspham.getyoulater.android.data.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendsActivity extends AppCompatActivity {
    public final static String TAG = FriendsActivity.class.getSimpleName();

    @BindView(R.id.firstname)
    EditText firtName;
    @BindView(R.id.lastname)
    EditText lastName;
    @BindView(R.id.age)
    EditText age;
    @BindView(R.id.list_user)
    RecyclerView listUser;

    AppDatabase appDatabase;
    UserDao userDao;

    @OnClick(R.id.fab)
    public void onAddButtonClicked(){
        User user = new User();
        user.setFirstName(firtName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setAge(Integer.parseInt(age.getText().toString()));
        userDao.insert(user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appDatabase = AppDatabase.getInstance(this);
        userDao = appDatabase.userDao();

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        listUser.setLayoutManager(lm);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });
        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
