package com.techeducation.groupmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);

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
}