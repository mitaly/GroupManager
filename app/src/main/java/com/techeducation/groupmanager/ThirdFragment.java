package com.techeducation.groupmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class ThirdFragment extends Fragment {
    List allUsersDataList ;
    AllUsersAdapter adapter;
    RecyclerView recyclerView;
    EditText eTxtSearch;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerAllUsers);
        eTxtSearch = (EditText)view.findViewById(R.id.eTxtSearch);
        allUsersDataList = new ArrayList<AllUsersBean>();
        initList();
        adapter = new AllUsersAdapter(allUsersDataList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
        return  view;
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
