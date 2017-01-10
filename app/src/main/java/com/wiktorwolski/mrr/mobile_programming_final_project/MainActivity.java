package com.wiktorwolski.mrr.mobile_programming_final_project;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;
    private String[] items;
    private int userID;
    SharedPreferences sharedPreferences;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String activityTitle;
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
        activityTitle = getTitle().toString();

        items = getResources().getStringArray(R.array.navigation_drawer_items);

        list = (ListView) findViewById(R.id.listNavigation);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
        list.setOnItemClickListener(this);

        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ChallengeFragment fragment = new ChallengeFragment();

        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.mainConntent, fragment, "A").commit();
    }

    private void setupDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {

                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(activityTitle);
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
                ChallengeList();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case 5:
                Logout();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case 4:
                Add();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case 2:
                drawerLayout.closeDrawer(Gravity.LEFT);
                startDialogTest();
                break;

            case 3:
                UserProfile();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
    }

    public void UserProfile() {

        UserProfileFragment fragment = new UserProfileFragment();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.mainConntent, fragment, "B");
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
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.mainConntent, fragment, "C");
        transaction.commit();
    }

    public void startDialogTest() {

        manager = getFragmentManager();
        AddChallengeFragment fragment = new AddChallengeFragment();
        fragment.show(manager, "MyDialog");
    }

    public  void Add(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.add_challenge_fragment, null);
        final EditText input1 = (EditText) textEntryView.findViewById(R.id.tTitle);
        final EditText input2 = (EditText) textEntryView.findViewById(R.id.tDescription);
        final EditText input3 = (EditText) textEntryView.findViewById(R.id.tDeadLine);
        input1.setText("", TextView.BufferType.EDITABLE);
        input2.setText("", TextView.BufferType.EDITABLE);
        input3.setText("", TextView.BufferType.EDITABLE);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New Challenge").setView(textEntryView).setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                        ChallengeHandler challengeHandler = new ChallengeHandler(MainActivity.this, null, null, 1);
                        Challenge challenge = new Challenge();
                        challenge.setIdOfUser(userID);
                        challenge.setTitle(input1.getText().toString());
                        challenge.setDescription(input2.getText().toString());
                        challenge.setDeadline(input3.getText().toString());
                        challenge.setIconId(0);
                        challenge.setDone(false);
                        challengeHandler.addChallenge(challenge);
                        finish();
                        startActivity(getIntent());

                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                    }
                });
        alert.show();
    }
}
