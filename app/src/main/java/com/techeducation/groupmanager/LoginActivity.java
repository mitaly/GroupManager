package com.techeducation.groupmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.password;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etxtemail, etxtpass;
    TextView tvRegister;
    Button btnlogin;
    CheckBox chkboxPass;

    void initViews(){
        chkboxPass = (CheckBox)findViewById(R.id.chkboxShwPass);
        tvRegister = (TextView)findViewById(R.id.register);
        etxtpass = (EditText)findViewById(R.id.password);
        etxtemail = (EditText)findViewById(R.id.emailid);
        btnlogin = (Button)findViewById(R.id.login);

        btnlogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

        chkboxPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    etxtpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etxtpass.setSelection(etxtpass.getText().length());
                }else{
                    etxtpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etxtpass.setSelection(etxtpass.getText().length());
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_login);
        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login :

                if(!isValidEmail(etxtemail.getText().toString().trim())){
                    etxtemail.setError("Invalid Email");
                }
                else if(etxtpass.getText().toString().isEmpty()){
                    etxtpass.setError("Invalid Password");
                }else {

                Intent i = new Intent(LoginActivity.this, StudentPanelActivity.class);
                startActivity(i);
                }

                break;

            case R.id.register :
                etxtpass.setText("");
                etxtemail.setText("");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

                break;
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}