package com.techeducation.groupmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    TextView txtUserStats,txtRecentUsers;
    List recentUsersDataList;
    CardView cardViewRecentUsers;
    AllUsersAdapter adapter;
    ProgressBar progressBar;
    LinearLayout linearLayoutAdmin;

    void initViews(){
        txtUserStats = (TextView)findViewById(R.id.userStats);
        txtRecentUsers = (TextView)findViewById(R.id.txtViewRecentUsers);
        cardViewRecentUsers = (CardView)findViewById(R.id.cardRecentUsers);

        //user stats
        handler.sendEmptyMessage(100);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerRecentUsers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recent user list
        handler.sendEmptyMessage(200);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayoutAdmin = (LinearLayout)findViewById(R.id.linLayoutAdmin);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        linearLayoutAdmin.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initViews();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.postEvent){
            Intent i = new Intent(AdminActivity.this,PostEventActivity.class);
            startActivity(i);
        }
        else if (id == R.id.viewProfile) {
            Intent i =new Intent(AdminActivity.this,ProfileActivity.class);
            startActivity(i);

        } else if (id == R.id.viewMember) {
            Intent i = new Intent(AdminActivity.this,ViewAllUsers.class);
            startActivity(i);

        } else if (id == R.id.addMember) {
            Intent i = new Intent(AdminActivity.this,AddUsersActivity.class);
            startActivity(i);

        } else if (id == R.id.logout) {
            UserSessionManager sessionManager = new UserSessionManager(getApplicationContext());
            sessionManager.logoutUser();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                                    Toast.makeText(AdminActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
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
                                            startActivity(i);

                                        }

                                        @Override public void onLongItemClick(View view, int position) {

                                        }
                                    })
                            );

                            progressBar.setVisibility(View.GONE);
                            linearLayoutAdmin.setVisibility(View.VISIBLE);
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

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }


    }
}
