package com.wiktorwolski.mrr.mobile_programming_final_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;
    private String[] items;
    private int userID;
    SharedPreferences sharedPreferences;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    FragmentManager manager;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt(LoginActivity.USER_ID, -1);

        System.out.println(userID);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        items = getResources().getStringArray(R.array.navigation_drawer_items);

        list = (ListView) findViewById(R.id.listNavigation);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
        list.setOnItemClickListener(this);

        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ChallengeFragment fragment = new ChallengeFragment();
        fragment.setStatus(0);

        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.mainConntent, fragment, "A").commit();
    }

    private void setupDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {

                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch(position) {

            case 0:
                getSupportActionBar().setTitle("Pending Challenges");
                ChallengeList();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case 1:
                getSupportActionBar().setTitle("Done Challenges");
                DoneChallengeList();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case 5:
                Logout();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case 4:
                getSupportActionBar().setTitle("About Us");
                AboutUs();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case 2:
                drawerLayout.closeDrawer(Gravity.LEFT);
                startAddChallengeFragment();
                break;

            case 3:
                getSupportActionBar().setTitle("Your Profile");
                UserProfile();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
    }

    public void UserProfile() {

        UserProfileFragment fragment = new UserProfileFragment();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.mainConntent, fragment, "C");
        transaction.commit();
    }

    public void Logout() {

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void ChallengeList() {

        ChallengeFragment fragment = new ChallengeFragment();
        fragment.setStatus(0);
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.mainConntent, fragment, "A");
        transaction.commit();
    }

    public void startAddChallengeFragment() {

        manager = getFragmentManager();
        AddChallengeFragment fragment = new AddChallengeFragment();
        fragment.show(manager, "MyDialog");
    }

    public void DoneChallengeList() {

        ChallengeFragment fragment = new ChallengeFragment();
        fragment.setStatus(1);

        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.mainConntent, fragment, "B");
        transaction.commit();
    }

    public void AboutUs() {

        AboutUsFragment fragment = new AboutUsFragment();

        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.mainConntent, fragment, "D");
        transaction.commit();
    }
}
