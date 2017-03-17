package com.techeducation.groupmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    LinearLayout LLTechKnown,LLLangKnown, LLProjDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LLTechKnown = (LinearLayout)findViewById(R.id.LLTechKnown);
        LLLangKnown = (LinearLayout)findViewById(R.id.LLLangKnown);
        LLProjDetails = (LinearLayout)findViewById(R.id.LLProjDetails);

        String TechnArr[] = {"Android", "PHP", "Artificial Intelligence"};
        String LangArr[] = {"Java", "C++", "Python"};
        String ProjDetails[] = {"Tech-Reveal", "Imagica", "Groupmanager"};

        for(int i = 0; i < 3; i++) {
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this);
            textView.setLayoutParams(lparams);
            textView.setText(TechnArr[i]);
            textView.setTextSize(16);
            lparams.setMargins(20,8,8,8);
            this.LLTechKnown.addView(textView);
        }

        for(int i = 0; i < 3; i++) {
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this);
            textView.setLayoutParams(lparams);
            textView.setText(LangArr[i]);
            textView.setTextSize(16);
            lparams.setMargins(20,8,8,8);
            this.LLLangKnown.addView(textView);
        }

        for(int i = 0; i < 3; i++) {
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this);
            textView.setLayoutParams(lparams);
            textView.setText(ProjDetails[i]);
            textView.setTextSize(16);
            lparams.setMargins(20,8,8,8);
            this.LLProjDetails.addView(textView);
        }
    }
}