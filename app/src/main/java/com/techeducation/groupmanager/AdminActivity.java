package com.techeducation.groupmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private static final int ADD_USERS_ID=1;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    void setUpViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Fragments need to be added
        viewPagerAdapter.addFragment(new FirstFragment(),"Post Event");
        viewPagerAdapter.addFragment(new SecondFragment(),"Events");
        viewPagerAdapter.addFragment(new ThirdFragment(),"View Users");
        viewPager.setAdapter(viewPagerAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentNameList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentNameList.get(position);
        }

        void addFragment(Fragment fragment,String name){
            mFragmentList.add(fragment);
            mFragmentNameList.add(name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, 1 , Menu.NONE,"Add Users");
        menu.add(Menu.NONE, 2 , Menu.NONE,"View Profile");
        menu.add(Menu.NONE, 3 , Menu.NONE,"Logout");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1){
            Toast.makeText(this,"add "+1,Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId()==2){
            Intent i = new Intent(AdminActivity.this,ProfileActivity.class);
            startActivity(i);
        }
        else if(item.getItemId()==3){
            UserSessionManager session;
            session=new UserSessionManager(getApplicationContext());
            session.logoutUser();
            finish();
        }
        return true;
    }
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
