package com.techeducation.groupmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    String email;
    public static int access,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        UserSessionManager session = new UserSessionManager(this);
        HashMap map = session.getUserDetails();
        email = (String) map.get(UserSessionManager.KEY_EMAIL);
        access = (int) map.get(UserSessionManager.KEY_ACCESS);
        user_id=(int)map.get(UserSessionManager.KEY_USER_ID);

        if (session.checkLogin()) {
            finish();
        }

        Log.i("show","access   "+access+"");
        switch (access) {
            case 1:
                Intent i = new Intent(StartActivity.this, AdminActivity.class);
//                i.putExtra("access",access);
//                i.putExtra("user_id",user_id);
                startActivity(i);
                break;
            case 2:
                Intent j = new Intent(StartActivity.this, StudentPanelActivity.class);
//                j.putExtra("access",access);
//                j.putExtra("user_id",user_id);
                startActivity(j);
                break;
            case 3:
                Intent k = new Intent(StartActivity.this, StudentPanelActivity.class);
//                k.putExtra("access",access);
//                k.putExtra("user_id",user_id);
                startActivity(k);
                break;
        }
    }
}
