
package com.techeducation.groupmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    LinearLayout LLTechKnown,LLLangKnown, LLProjDetails;
    int user_id_other,user_id;
    TextView username,email,phone,skype,github,branch,year;
    RadioGroup radioGrpDaySch;
    RadioButton rbDayScYes,rbDayScNo;
    ToggleButton toggleSuspend;

    void initViews() {
        user_id = StartActivity.user_id;
        Intent rcv = getIntent();
        user_id_other = rcv.getIntExtra("keyUserId",0);

        LLTechKnown = (LinearLayout)findViewById(R.id.LLTechKnown);
        LLLangKnown = (LinearLayout)findViewById(R.id.LLLangKnown);
        LLProjDetails = (LinearLayout)findViewById(R.id.LLProjDetails);

        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        skype = (TextView) findViewById(R.id.skype);
        phone = (TextView) findViewById(R.id.phone);
        github = (TextView) findViewById(R.id.github);
        branch = (TextView)findViewById(R.id.branch);
        year = (TextView)findViewById(R.id.year);
        radioGrpDaySch = (RadioGroup)findViewById(R.id.radioGrpDaySch);
        rbDayScYes = (RadioButton)findViewById(R.id.DaySchYes);
        rbDayScNo = (RadioButton)findViewById(R.id.DaySchNo);

        handler.sendEmptyMessage(100);

        //if admin only then can edit profile
        if(user_id==1){
            toggleSuspend = (ToggleButton)findViewById(R.id.toggleSuspend);
            toggleSuspend.setVisibility(View.VISIBLE);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEditProfile);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        initViews();


    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            JSONObject jsonObject = new JSONObject();
            try {
                if(user_id_other==0){
                    jsonObject.put("user_id",StartActivity.user_id);
                }
                else{
                    jsonObject.put("user_id",user_id_other);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ConnectionUtil.PHP_VIEW_PROFILE_URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
//                            Log.i("show",jsonObject.toString());
                            JSONObject profileObject = jsonObject.getJSONObject("profile");
//                            Log.i("show",profileObject.toString());
                            JSONObject userPersonalInfo = profileObject.getJSONObject("user_personal");
                            String username1 = userPersonalInfo.getString("username");
                            String email1 = userPersonalInfo.getString("email");
                            String phone1 = userPersonalInfo.getString("phone_no");
                            String skype1 = userPersonalInfo.getString("skype_id");
                            String github1 = userPersonalInfo.getString("github");
//                            Log.i("show","username: "+username1+" email: "+email1+" phone1: "+phone1+" skyype: "+skype1+" github: "+github1);
                            username.setText(username1);
                            email.setText(email1);
                            phone.setText(phone1);
                            skype.setText(skype1);
                            github.setText(github1);

                            branch.setText(userPersonalInfo.getString("branch"));
                            year.setText(userPersonalInfo.getString("year"));
                            if(userPersonalInfo.getInt("dayscholar")==1){
                                rbDayScYes.setChecked(true);
                            }
                            else{
                                rbDayScNo.setChecked(true);
                            }

                            JSONArray languageArray = profileObject.getJSONArray("languages");
                            JSONArray technologiesArray = profileObject.getJSONArray("technologies");

//                            Log.i("show","Languges: "+languageArray);
//                            Log.i("show","Technologies: "+technologiesArray);
                            for(int i=0;i<languageArray.length();i++){
                                TextView textView = new TextView(getApplicationContext());
                                textView.setText(languageArray.getString(i));
                                textView.setTextSize(16);
                                textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                                LLLangKnown.addView(textView);
                            }

                            for(int i=0;i<technologiesArray.length();i++){

                                TextView textView = new TextView(getApplicationContext());
                                textView.setText(technologiesArray.getString(i));
                                textView.setTextSize(16);
                                textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                                LLTechKnown.addView(textView);
                            }


                            JSONArray projectsArray = profileObject.getJSONArray("projects");
//                            Log.i("show",projectsArray.toString());
                            for(int i=0;i<projectsArray.length();i++){
                                JSONObject projectObject = (JSONObject) projectsArray.get(i);
//                                Log.i("show",projectObject.toString());
//                                Log.i("show","inside projects");
//                                Log.i("show","Details before: "+projectObject.getString("proj_title"));

                                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                LinearLayout llProjectTitle = new LinearLayout(getApplicationContext());
                                llProjectTitle.setLayoutParams(lparams);
                                llProjectTitle.setOrientation(LinearLayout.HORIZONTAL);
                                llProjectTitle.setWeightSum(1);

                                TextView txtNumber = new TextView(getApplicationContext());
                                txtNumber.setTextSize(16);
                                String number = (i+1)+".";
                                txtNumber.setText(number);
                                txtNumber.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));

                                TextView textTitle = new TextView(getApplicationContext());
                                textTitle.setTextSize(16);
                                textTitle.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));

                                textTitle.setText(projectObject.getString("proj_title"));


                                llProjectTitle.addView(txtNumber);
                                llProjectTitle.addView(textTitle);

                                LLProjDetails.addView(llProjectTitle);

                                if(!projectObject.getString("proj_url").equals("")){
                                    LinearLayout llProjectUrl = new LinearLayout(getApplicationContext());
                                    llProjectUrl.setLayoutParams(lparams);

                                    llProjectUrl.setOrientation(LinearLayout.HORIZONTAL);
                                    llProjectUrl.setWeightSum(1);

                                    TextView txtNumber1 = new TextView(getApplicationContext());
                                    txtNumber1.setTextSize(16);
                                    txtNumber1.setText(String.format("%"+number.length()+"s",""));

                                    TextView textUrl = new TextView(getApplicationContext());
                                    textUrl.setTextSize(16);
                                    textUrl.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
                                    textUrl.setText(projectObject.getString("proj_url"));

                                    llProjectUrl.addView(txtNumber1);
                                    llProjectUrl.addView(textUrl);
                                    LLProjDetails.addView(llProjectUrl);
                                }

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,20);
                                View view = new View(getApplicationContext());
                                view.setLayoutParams(params);
                               LLProjDetails.addView(view);

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
                AppController.getInstance().addToRequestQueue(jsonObjectRequest, "json_obj_req");



        }
    };
}