package com.techeducation.groupmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class StudentPanelActivity extends AppCompatActivity {

    List allUsersDataList ;
     AllUsersAdapter adapter;
    RecyclerView recyclerView;
    UserSessionManager session;

    int access,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Intent i= getIntent();
//        access=i.getIntExtra("access",0);
//        user_id=i.getIntExtra("user_id",0);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerAllUsers);
        allUsersDataList = new ArrayList<AllUsersBean>();
        initList();
        adapter = new AllUsersAdapter(allUsersDataList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);


    }

    void initList(){

        allUsersDataList.add(new AllUsersBean(R.drawable.pic2,"Gurkirat Kaur"));
        allUsersDataList.add(new AllUsersBean(R.drawable.pic3,"Jaspreet Kaur"));
        allUsersDataList.add(new AllUsersBean(R.drawable.pic4,"Amritpal Kaur"));
        allUsersDataList.add(new AllUsersBean(R.drawable.pic5,"Komal Sharma"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.student_panel_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.viewProfile:
                Intent i =new Intent(StudentPanelActivity.this,ProfileActivity.class);
                startActivity(i);
                break;
            case R.id.logout:
                session=new UserSessionManager(getApplicationContext());
                session.logoutUser();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }


}