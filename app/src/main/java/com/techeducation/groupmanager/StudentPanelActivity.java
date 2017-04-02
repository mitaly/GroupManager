package com.techeducation.groupmanager;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class StudentPanelActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserSessionManager session;
    TextView txtUserStats,txtRecentUsers;
    Button btnViewUsers;
    List recentUsersDataList;
    CardView cardViewRecentUsers;
    AllUsersAdapter adapter;

    void initViews(){
        txtUserStats = (TextView)findViewById(R.id.userStats);
        txtRecentUsers = (TextView)findViewById(R.id.txtViewRecentUsers);
        btnViewUsers = (Button)findViewById(R.id.btnViewUsers);
        cardViewRecentUsers = (CardView)findViewById(R.id.cardRecentUsers);

        //user stats
        handler.sendEmptyMessage(100);


           if(StartActivity.access==3)
           {
               txtRecentUsers.setVisibility(View.GONE);
               cardViewRecentUsers.setVisibility(View.GONE);
               btnViewUsers.setVisibility(View.GONE);
           }
            else{
               recyclerView = (RecyclerView)findViewById(R.id.recyclerRecentUsers);
               RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
               recyclerView.setLayoutManager(mLayoutManager);
               recyclerView.setItemAnimator(new DefaultItemAnimator());
               //recent user list
               handler.sendEmptyMessage(200);
               btnViewUsers.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       //all user list
                       handler.sendEmptyMessage(300);
                   }
               });

           }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();

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

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==100){
                StringRequest strUserCountRequest = new StringRequest(Request.Method.POST, ConnectionUtil.PHP_COUNT_USERS_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            switch (jsonObject.getInt("error")){
                                case 0:
                                    txtUserStats.setText(String.valueOf(jsonObject.getInt("num_rows")));
                                    break;
                                case 1:
                                    Toast.makeText(StudentPanelActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                AppController.getInstance().addToRequestQueue(strUserCountRequest, "json_obj_req");
            }
            else if(msg.what==200){
                final int allUsers = 0;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ConnectionUtil.PHP_LIST_OF_USERS_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {

                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray userArray = jsonObject.getJSONArray("userArray");

                            Log.i("show",userArray.toString());
                                recentUsersDataList = new ArrayList();
                                for(int i=0;i<userArray.length();i++){
                                    JSONObject jsonObject1 = userArray.getJSONObject(i);
                                    recentUsersDataList.add(new AllUsersBean(jsonObject1.getInt("user_id"),jsonObject1.getString("username")));

                                adapter = new AllUsersAdapter(recentUsersDataList);
                                recyclerView.setAdapter(adapter);
                            }

                            recyclerView.addOnItemTouchListener(
                                    new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override public void onItemClick(View view, int position) {
                                                Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
                                                AllUsersBean allUsersBean = (AllUsersBean) recentUsersDataList.get(position);
                                                i.putExtra("keyUserId",allUsersBean.getuserid());
                                                getApplicationContext().startActivity(i);

                                        }

                                        @Override public void onLongItemClick(View view, int position) {

                                        }
                                    })
                            );
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Some Connectivity Error",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("show",volleyError.toString());
                        Toast.makeText(getApplicationContext(),"Some Connectivity Error",Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("allUsers",String.valueOf(allUsers));
                        params.put("user_id",String.valueOf(StartActivity.user_id));
                        return params;
                    }
                };

                AppController.getInstance().addToRequestQueue(stringRequest, "json_obj_req");

            }
            else if(msg.what==300){
                Intent i = new Intent(StudentPanelActivity.this,ViewAllUsers.class);
                startActivity(i);
            }
        }
    };

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