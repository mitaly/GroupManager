package com.techeducation.groupmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mitaly on 25/3/17.
 */

public class ShowUsersClass {
    boolean hasUsers = true;
    Context context;
    List allUsersDataList,recentUsersDataList ;
    AllUsersAdapter adapter;
    RecyclerView recyclerView;
    int allUsers;

    public  ShowUsersClass(Context context){

            this.context = context;
    }
    public void init(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }
    public boolean checkUserList(){
        StringRequest strReqNumUsers = new StringRequest(Request.Method.POST, ConnectionUtil.PHP_COUNT_USERS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if(jsonObject.getInt("num_rows")>0){
                        hasUsers = true;
                    }
                    else{
                        hasUsers = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Toast.makeText(getActivity(),"Some Connectivity Error",Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(strReqNumUsers, "json_obj_req");
        return hasUsers;
    }

    //i=0 for recent user ---i=1 for all users
    void populateUsersList(int i){

        allUsers = i;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConnectionUtil.PHP_LIST_OF_USERS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {

                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray userArray = jsonObject.getJSONArray("userArray");

                    if(allUsers==1){
                        allUsersDataList = new ArrayList();
                        for(int i=0;i<userArray.length();i++){
                            JSONObject jsonObject1 = userArray.getJSONObject(i);
                            allUsersDataList.add(new AllUsersBean(jsonObject1.getInt("user_id"),jsonObject1.getString("username")));
                        }
                        adapter = new AllUsersAdapter(allUsersDataList);
                        recyclerView.setAdapter(adapter);
                    }

                    else if(allUsers==0){
                        recentUsersDataList = new ArrayList();
                        for(int i=0;i<userArray.length();i++){
                            JSONObject jsonObject1 = userArray.getJSONObject(i);
                            recentUsersDataList.add(new AllUsersBean(jsonObject1.getInt("user_id"),jsonObject1.getString("username")));
                        }
                        adapter = new AllUsersAdapter(recentUsersDataList);
                        recyclerView.setAdapter(adapter);
                    }




                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                @Override public void onItemClick(View view, int position) {
                                    if(allUsers==1){
                                        Intent i = new Intent(context,ProfileActivity.class);
                                        AllUsersBean allUsersBean = (AllUsersBean) allUsersDataList.get(position);
                                        i.putExtra("keyUserId",allUsersBean.getuserid());
                                        context.startActivity(i);
                                    }
                                    else if(allUsers==0){
                                        Intent i = new Intent(context,ProfileActivity.class);
                                        AllUsersBean allUsersBean = (AllUsersBean) recentUsersDataList.get(position);
                                        i.putExtra("keyUserId",allUsersBean.getuserid());
                                        context.startActivity(i);
                                    }
                                }

                                @Override public void onLongItemClick(View view, int position) {

                                }
                            })
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Some Connectivity Error",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("show",volleyError.toString());
                Toast.makeText(context,"Some Connectivity Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("allUsers",String.valueOf(allUsers));
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, "json_obj_req");
    }

    void updateList(String s){
        adapter.listUpdate(s);
    }
}
