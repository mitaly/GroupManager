package com.techeducation.groupmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
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
        handler.sendEmptyMessage(100);
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
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConnectionUtil.PHP_LIST_OF_USERS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {
                        Log.i("show",s);
                        JSONObject jsonObject = new JSONObject(s);
                        Gson gson = new Gson();
                        JSONArray userArray = jsonObject.getJSONArray("userArray");
                        Type listType = new TypeToken<List<AllUsersBean>>(){}.getType();
                        allUsersDataList = (ArrayList<AllUsersBean>)gson.fromJson(userArray.toString(),listType);
                        adapter = new AllUsersAdapter(allUsersDataList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                            @Override
                            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                                return false;
                            }

                            @Override
                            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                            }

                            @Override
                            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),"Some Connectivity Error",Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.i("show",volleyError.toString());
                    Toast.makeText(getActivity(),"Some Connectivity Error",Toast.LENGTH_LONG).show();
                }
            });

            AppController.getInstance().addToRequestQueue(stringRequest, "json_obj_req");
        }
    };


}
