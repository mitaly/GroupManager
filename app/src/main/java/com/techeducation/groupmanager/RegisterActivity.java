package com.techeducation.groupmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    JSONArray arrayTech, arrayLang, arrayProjects;
    JSONObject objProjects, jsonRequestObject;
    ImageView imgArrow;
    RadioGroup rGrpYear, rGrpSchHost;
    RadioButton rbYear, rbDayHost;
    Button btnRegister;
    EditText eTxtTech, eTxtName, eTxtEmail, eTxtPhone, eTxtSkype, eTxtGithub, eTxtLang, eTxtProjTitle, eTxtProjURL, eTxtPassword;
    ImageButton btnAddTech, btnAddLang, btnAddProj;
    Spinner spinnerBranch;
    LinearLayout linearLayoutTech, linLayoutHoriTech, linearLayoutLang, linLayoutHoriLang, linearLayoutProj, linLayoutVertProj, linLayoutProjTitle;
    ArrayList<EditText> listRefTechETxt, listRefLangETxt, listRefProjTitleETxt, listRefProjURLETxt;
    ArrayList<ImageButton> listRefTechBtn, listRefLangBtn, listRefProjBtn;
    ArrayList<LinearLayout> listRefTechLinLayout, listRefLangLinLayout, listRefProjTitleLinLayout;
    static int idTech, idLang, idProj;

    void initViews() {
        eTxtName = (EditText) findViewById(R.id.eTxtName);
        eTxtEmail = (EditText) findViewById(R.id.eTxtEmail);
        eTxtPhone = (EditText) findViewById(R.id.eTxtPhone);
        eTxtSkype = (EditText) findViewById(R.id.eTxtSkype);
        eTxtGithub = (EditText) findViewById(R.id.eTxtGithub);
        eTxtPassword = (EditText) findViewById(R.id.eTxtPassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        rGrpYear = (RadioGroup) findViewById(R.id.radioGrpYear);
        rGrpSchHost = (RadioGroup) findViewById(R.id.radioGrpSchHost);

        imgArrow = (ImageView) findViewById(R.id.imgArrow);

        spinnerBranch = (Spinner) findViewById(R.id.spinnerBranch);

        eTxtTech = (EditText) findViewById(R.id.eTxtTech);
        btnAddTech = (ImageButton) findViewById(R.id.btnTechAdd);

        eTxtLang = (EditText) findViewById(R.id.eTxtLang);
        btnAddLang = (ImageButton) findViewById(R.id.btnLangAdd);

        eTxtProjTitle = (EditText) findViewById(R.id.eTxtProjTitle);
        eTxtProjURL = (EditText) findViewById(R.id.eTxtProjUrl);
        btnAddProj = (ImageButton) findViewById(R.id.btnAddProj);

        linearLayoutTech = (LinearLayout) findViewById(R.id.linearLayoutTech);
        linLayoutHoriTech = (LinearLayout) findViewById(R.id.linLayoutHorizTech);

        linearLayoutLang = (LinearLayout) findViewById(R.id.linearLayoutLang);
        linLayoutHoriLang = (LinearLayout) findViewById(R.id.linLayoutHorizLang);

        linearLayoutProj = (LinearLayout) findViewById(R.id.linearLayoutProj);
        linLayoutVertProj = (LinearLayout) findViewById(R.id.linLayoutVertProj);
        linLayoutProjTitle = (LinearLayout) findViewById(R.id.linLayoutProjTitle);


        listRefTechETxt = new ArrayList();
        listRefTechBtn = new ArrayList();
        listRefTechLinLayout = new ArrayList();

        listRefLangETxt = new ArrayList();
        listRefLangBtn = new ArrayList();
        listRefLangLinLayout = new ArrayList();

        listRefProjTitleETxt = new ArrayList();
        listRefProjURLETxt = new ArrayList();
        listRefProjBtn = new ArrayList();
        listRefProjTitleLinLayout = new ArrayList();

        setDrawables();

        setSpinnerBranch();

//        rGrpYear.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                rbYear = (RadioButton) findViewById(group.getCheckedRadioButtonId());
//            }
//        });
//
//        rGrpSchHost.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                rbDayHost = (RadioButton) findViewById(group.getCheckedRadioButtonId());
//            }
//        });


        btnAddTech.setOnClickListener(this);

        btnAddLang.setOnClickListener(this);

        btnAddProj.setOnClickListener(this);

        btnRegister.setOnClickListener(this);
    }


    void setSpinnerBranch() {
        final String[] branches = {"Computer Science", "Information Technology", "Electrical Engineering", "Electronics & Communication", "Civil Engineering", "Mechanical Engineering"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branches);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(dataAdapter);

        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),"Chosen ",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void setDrawables() {

        Drawable img = this.getResources().getDrawable(R.drawable.user23);
        img.setBounds(0, 0, 40, 40);
        eTxtName.setCompoundDrawables(img, null, null, null);

        img = this.getResources().getDrawable(R.drawable.email);
        img.setBounds(0, 0, 40, 40);
        eTxtEmail.setCompoundDrawables(img, null, null, null);

        img = this.getResources().getDrawable(R.drawable.phone);
        img.setBounds(0, 0, 40, 40);
        eTxtPhone.setCompoundDrawables(img, null, null, null);

        img = this.getResources().getDrawable(R.drawable.skype);
        img.setBounds(0, 0, 40, 40);
        eTxtSkype.setCompoundDrawables(img, null, null, null);

        img = this.getResources().getDrawable(R.drawable.github);
        img.setBounds(0, 0, 40, 40);
        eTxtGithub.setCompoundDrawables(img, null, null, null);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister: {
                handler.sendEmptyMessage(100);
                break;
            }

            case R.id.btnTechAdd: {
                boolean flag = true;

                if (eTxtTech.getText().toString().trim().equals("")) {
                    eTxtTech.setError("Field can't be empty");
                    flag = false;
                } else if (!listRefTechETxt.isEmpty()) {
                    for (int i = 0; i < listRefTechETxt.size(); i++) {
                        if (listRefTechETxt.get(i).getText().toString().trim().equals("")) {
                            listRefTechETxt.get(i).setError("Field can't be empty");
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    EditText eTxtTechMore = (EditText) getLayoutInflater().inflate(R.layout.edit_text_layout, null);
                    ImageButton btnDeleteTech = new ImageButton(this);
                    LinearLayout linearLayoutMoreTech = new LinearLayout(this);

                    btnDeleteTech.setImageResource(R.drawable.minus24);
                    btnDeleteTech.setBackgroundColor(Color.TRANSPARENT);

                    eTxtTechMore.setLayoutParams(eTxtTech.getLayoutParams());
                    btnDeleteTech.setLayoutParams(btnAddTech.getLayoutParams());
                    linearLayoutMoreTech.setLayoutParams(linLayoutHoriTech.getLayoutParams());

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    btnDeleteTech.setLayoutParams(layoutParams);

                    eTxtTechMore.setSingleLine();

                    eTxtTechMore.setId(idTech);
                    btnDeleteTech.setId(idTech);
                    linearLayoutMoreTech.setId(idTech);
                    idTech++;

                    listRefTechETxt.add(eTxtTechMore);
                    listRefTechBtn.add(btnDeleteTech);
                    listRefTechLinLayout.add(linearLayoutMoreTech);

                    linearLayoutMoreTech.addView(eTxtTechMore);
                    linearLayoutMoreTech.addView(btnDeleteTech);


                    linearLayoutTech.addView(linearLayoutMoreTech);

                    if (!listRefTechBtn.isEmpty()) {
                        for (int i = 0; i < listRefTechBtn.size(); i++) {

                            listRefTechBtn.get(i).setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    int index = listRefTechBtn.indexOf((ImageButton) v);
                                    linearLayoutTech.removeView(listRefTechLinLayout.get(index));
                                    listRefTechETxt.remove(index);
                                    listRefTechBtn.remove(index);
                                    listRefTechLinLayout.remove(index);

                                }
                            });
                        }
                    }

                }

                break;
            }

            case R.id.btnLangAdd: {

                boolean flag = true;

                if (eTxtLang.getText().toString().trim().equals("")) {
                    eTxtLang.setError("Field can't be empty");
                    flag = false;
                } else if (!listRefLangETxt.isEmpty()) {
                    for (int i = 0; i < listRefLangETxt.size(); i++) {
                        if (listRefLangETxt.get(i).getText().toString().trim().equals("")) {
                            listRefLangETxt.get(i).setError("Field can't be empty");
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    EditText eTxtLangMore = (EditText) getLayoutInflater().inflate(R.layout.edit_text_layout, null);
                    ImageButton btnDeleteLang = new ImageButton(this);
                    LinearLayout linearLayoutMoreLang = new LinearLayout(this);

                    btnDeleteLang.setImageResource(R.drawable.minus24);
                    btnDeleteLang.setBackgroundColor(Color.TRANSPARENT);

                    eTxtLangMore.setLayoutParams(eTxtLang.getLayoutParams());
                    btnDeleteLang.setLayoutParams(btnAddLang.getLayoutParams());
                    linearLayoutMoreLang.setLayoutParams(linLayoutHoriLang.getLayoutParams());


                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    btnDeleteLang.setLayoutParams(layoutParams);

                    eTxtLangMore.setSingleLine();

                    eTxtLangMore.setId(idLang);
                    btnDeleteLang.setId(idLang);
                    linearLayoutMoreLang.setId(idLang);
                    idLang++;

                    listRefLangETxt.add(eTxtLangMore);
                    listRefLangBtn.add(btnDeleteLang);
                    listRefLangLinLayout.add(linearLayoutMoreLang);

                    linearLayoutMoreLang.addView(eTxtLangMore);
                    linearLayoutMoreLang.addView(btnDeleteLang);

                    linearLayoutLang.addView(linearLayoutMoreLang);

                    if (!listRefLangBtn.isEmpty()) {
                        for (int i = 0; i < listRefLangBtn.size(); i++) {

                            listRefLangBtn.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int index = listRefLangBtn.indexOf((ImageButton) v);
                                    linearLayoutLang.removeView(listRefLangLinLayout.get(index));
                                    listRefLangETxt.remove(index);
                                    listRefLangBtn.remove(index);
                                    listRefLangLinLayout.remove(index);

                                }
                            });
                        }
                    }

                }

                break;
            }

            case R.id.btnAddProj: {
                boolean flag = true;

                if (eTxtProjTitle.getText().toString().trim().equals("")) {
                    eTxtProjTitle.setError("Field can't be empty");
                    flag = false;
                } else if (!listRefProjTitleETxt.isEmpty()) {
                    for (int i = 0; i < listRefProjTitleETxt.size(); i++) {
                        if (listRefProjTitleETxt.get(i).getText().toString().trim().equals("")) {
                            listRefProjTitleETxt.get(i).setError("Field can't be empty");
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    EditText eTxtProjTitleMore = (EditText) getLayoutInflater().inflate(R.layout.edit_text_layout, null);
                    EditText eTxtProjURLMore = (EditText) getLayoutInflater().inflate(R.layout.edit_text_layout, null);
                    ImageView imageView = new ImageView(this);
                    ImageButton btnDeleteProj = new ImageButton(this);
                    LinearLayout linearLayoutMoreProjTitle = new LinearLayout(this);
                    LinearLayout linearLayoutMoreProj = new LinearLayout(this);

                    btnDeleteProj.setImageResource(R.drawable.minus24);
                    btnDeleteProj.setBackgroundColor(Color.TRANSPARENT);

                    eTxtProjTitleMore.setLayoutParams(eTxtProjTitle.getLayoutParams());
                    eTxtProjURLMore.setLayoutParams(eTxtProjURL.getLayoutParams());
                    imageView.setLayoutParams(imgArrow.getLayoutParams());
                    btnDeleteProj.setLayoutParams(btnAddProj.getLayoutParams());
                    linearLayoutMoreProjTitle.setLayoutParams(linLayoutProjTitle.getLayoutParams());
                    linearLayoutMoreProj.setLayoutParams(linearLayoutProj.getLayoutParams());
                    imageView.setImageResource(R.drawable.arrow);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    btnDeleteProj.setLayoutParams(layoutParams);

                    eTxtProjTitleMore.setSingleLine();
                    eTxtProjURLMore.setSingleLine();

                    eTxtProjTitleMore.setId(idProj);
                    eTxtProjURLMore.setId(idProj);
                    btnDeleteProj.setId(idProj);
                    linearLayoutMoreProjTitle.setId(idProj);
                    idTech++;

                    listRefProjTitleETxt.add(eTxtProjTitleMore);
                    listRefProjURLETxt.add(eTxtProjURLMore);
                    listRefProjBtn.add(btnDeleteProj);
                    listRefProjTitleLinLayout.add(linearLayoutMoreProjTitle);

                    linearLayoutMoreProjTitle.addView(imageView);
                    linearLayoutMoreProjTitle.addView(eTxtProjTitleMore);
                    linearLayoutMoreProjTitle.addView(btnDeleteProj);

                    linearLayoutProj.addView(linearLayoutMoreProjTitle);
                    linearLayoutProj.addView(eTxtProjURLMore);

                    if (!listRefProjBtn.isEmpty()) {
                        for (int i = 0; i < listRefProjBtn.size(); i++) {

                            listRefProjBtn.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int index = listRefProjBtn.indexOf((ImageButton) v);
                                    linearLayoutProj.removeView(listRefProjTitleLinLayout.get(index));
                                    linearLayoutProj.removeView(listRefProjURLETxt.get(index));
                                    listRefProjTitleETxt.remove(index);
                                    listRefProjURLETxt.remove(index);
                                    listRefProjBtn.remove(index);
                                    listRefProjTitleLinLayout.remove(index);
                                }
                            });
                        }
                    }

                }

                break;
            }
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String username, password, email, phone, skype, github, branch;
            int year = 0, daysch = 0;
            boolean flag = true;

            username = eTxtName.getText().toString().trim();
            password = eTxtPassword.getText().toString().trim();
            email = eTxtEmail.getText().toString().trim();
            phone = eTxtPhone.getText().toString().trim();
            skype = eTxtSkype.getText().toString().trim();
            github = eTxtGithub.getText().toString().trim();

            if (username.isEmpty()) {
                eTxtName.setError("Field can't be empty");
                flag = false;
            }
            if (!isValidEmail(email)) {
                eTxtEmail.setError("Invalid Email");
                flag = false;
            }
            if (password.isEmpty()) {
                eTxtPassword.setError("Field can't be empty");
                flag = false;
            }
            if (!(phone.length() == 10)) {
                eTxtPhone.setError("Invalid Phone number");
                flag = false;
            }
            if (skype.isEmpty()) {
                eTxtSkype.setError("Field can't be empty");
                flag = false;
            }
            if (!Patterns.WEB_URL.matcher(github).matches()) {
                eTxtGithub.setError("Invalid URL");
                flag = false;
            }

            if (flag) {
                if (rGrpYear.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Select Year", Toast.LENGTH_LONG).show();
                    flag = false;
                } else if (rGrpSchHost.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Select Day Scholar/ Hosteler", Toast.LENGTH_LONG).show();
                    flag = false;
                }

            }

            //validating Skills section
            if (flag) {
                arrayTech = new JSONArray();
                arrayLang = new JSONArray();


                if (!eTxtTech.getText().toString().trim().isEmpty()) {
                    arrayTech.put(eTxtTech.getText().toString().trim());
                }
                if (listRefTechETxt.size() != 0) {
                    for (int i = 0; i < listRefTechETxt.size(); i++) {
                        String str = listRefTechETxt.get(i).getText().toString().trim();
                        if (!str.isEmpty()) {
                            arrayTech.put(str);
                        }
                    }
                }

                if (!eTxtLang.getText().toString().trim().isEmpty()) {
                    arrayLang.put(eTxtLang.getText().toString().trim());
                }
                if (listRefLangETxt.size() != 0) {
                    for (int i = 0; i < listRefLangETxt.size(); i++) {
                        String str = listRefLangETxt.get(i).getText().toString().trim();
                        if (!str.isEmpty()) {
                            arrayLang.put(str);
                        }
                    }
                }

                objProjects = new JSONObject();
                arrayProjects = new JSONArray();

                if (!eTxtProjTitle.getText().toString().trim().isEmpty()) {
                    String title = eTxtProjTitle.getText().toString().trim();
                    String url = eTxtProjURL.getText().toString().trim();
                    try {
                        objProjects.put("ProjectTitle", title);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!url.isEmpty()) {
                        if (!Patterns.WEB_URL.matcher(url).matches()) {
                            eTxtProjURL.setError("Invalid URL");
                            flag = false;
                        }

                    }

                    if (flag) {
                        try {
                            objProjects.put("ProjectURL", url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        arrayProjects.put(objProjects);
                    }
                } else {
                    if (!eTxtProjURL.getText().toString().trim().isEmpty()) {
                        eTxtProjTitle.setError("Title is necessary");
                        flag = false;
                    }
                }
                //dynamic project list
                if (listRefProjTitleETxt.size() != 0) {

                    for (int i = 0; i < listRefProjTitleETxt.size(); i++) {
                        objProjects = new JSONObject();
                        String prTitle = listRefProjTitleETxt.get(i).getText().toString().trim();
                        String prUrl = listRefProjURLETxt.get(i).getText().toString().trim();

                        if (!prTitle.isEmpty()) {
                            try {
                                objProjects.put("ProjectTitle", prTitle);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (!prUrl.isEmpty()) {
                                if (!Patterns.WEB_URL.matcher(prUrl).matches()) {
                                    listRefProjURLETxt.get(i).setError("Invalid URL");
                                    flag = false;
                                }

                            }
                            if (flag) {
                                try {
                                    objProjects.put("ProjectURL", prUrl);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                arrayProjects.put(objProjects);
                            }
                        } else if (prTitle.isEmpty()) {
                            if (!prUrl.isEmpty()) {
                                listRefProjTitleETxt.get(i).setError("Title is necessary");
                                flag = false;
                            }
                        }
                    }
                }

            }

            //start
            if (flag) {

                jsonRequestObject = new JSONObject();
                ///object in map needs to be put
                try {
                    jsonRequestObject.put("username", username);
                    jsonRequestObject.put("email", email);
                    jsonRequestObject.put("password", password);
                    jsonRequestObject.put("phone", phone);
                    jsonRequestObject.put("skype", skype);
                    jsonRequestObject.put("github", github);

                    jsonRequestObject.put("branch", spinnerBranch.getSelectedItem().toString());

                    switch (rGrpYear.getCheckedRadioButtonId()) {
                        case R.id.radioBtn1stYear:
                            year = 1;
                            break;
                        case R.id.radioBtn2ndYear:
                            year = 2;
                            break;
                        case R.id.radioBtn3rdYear:
                            year = 3;
                            break;
                        case R.id.radioBtn4thYear:
                            year = 4;
                            break;
                    }

                    jsonRequestObject.put("year", year);

                    Log.i("show", "data inserted");

                    switch (rGrpSchHost.getCheckedRadioButtonId()) {
                        case R.id.rbDaySch:
                            daysch = 1;
                            break;
                        case R.id.rbHosteler:
                            daysch = 0;
                            break;
                    }
                    //1-Admin 2-Access-given Users 3-Normal users
                    jsonRequestObject.put("access", 3);
                    jsonRequestObject.put("dayscholar", daysch);
                    jsonRequestObject.put("technologies", arrayTech);
                    jsonRequestObject.put("languages", arrayLang);
                    jsonRequestObject.put("projects", arrayProjects);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, ConnectionUtil.PHP_REGISTER_URL, jsonRequestObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //Boolean error=jsonObject.getBoolean("error");
                        Log.i("show", jsonObject.toString());
                        try {
                            String msg = jsonObject.getString("msg");
                            int error = jsonObject.getInt("error");

                            switch (error) {
                                case 0:
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(RegisterActivity.this, StudentPanelActivity.class);
                                    startActivity(i);
                                    finish();
                                    break;
                                case 1:
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                    break;
                                case 2:
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.i("show", volleyError.toString());
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }
                };
                AppController.getInstance().addToRequestQueue(objRequest, "json_obj_req");
            }
        }

    };
}