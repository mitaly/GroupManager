package com.techeducation.groupmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ViewAllUsers extends AppCompatActivity {

    List allUsersDataList ;
    AllUsersAdapter adapter;
    RecyclerView recyclerView;
    EditText eTxtSearch;

    void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerAllUsers);
        eTxtSearch = (EditText) findViewById(R.id.eTxtSearch);
        allUsersDataList = new ArrayList<AllUsersBean>();
        initList();
        adapter = new AllUsersAdapter(allUsersDataList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        eTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.listUpdate(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_users);
        initViews();
    }

    void initList(){
        allUsersDataList.add(new AllUsersBean(R.drawable.pic,"Mitaly Sen"));
        allUsersDataList.add(new AllUsersBean(R.drawable.pic1,"Neha Yadav"));
        allUsersDataList.add(new AllUsersBean(R.drawable.pic2,"Gurkirat Kaur"));
        allUsersDataList.add(new AllUsersBean(R.drawable.pic3,"Jaspreet Kaur"));
        allUsersDataList.add(new AllUsersBean(R.drawable.pic4,"Amritpal Kaur"));
        allUsersDataList.add(new AllUsersBean(R.drawable.pic5,"Komal Sharma"));
        allUsersDataList.add(new AllUsersBean(R.drawable.pic6,"Ankita Malhotra"));
        allUsersDataList.add(new AllUsersBean(R.drawable.pic7,"Ruhanika Chopra"));
    }

}
