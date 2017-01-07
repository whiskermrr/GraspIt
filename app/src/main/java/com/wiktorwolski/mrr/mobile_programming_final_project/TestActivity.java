package com.wiktorwolski.mrr.mobile_programming_final_project;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TestActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listOfChallenges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ChallengeHandler challengeHandler = new ChallengeHandler(this, null, null, 1);

        SQLiteDatabase db = challengeHandler.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM challenges", null);

        ChallengeAdapter adapter = new ChallengeAdapter(this, cursor);

        listOfChallenges = (ListView) findViewById(R.id.listOfChallenges);
        listOfChallenges.setAdapter(adapter);
        listOfChallenges.setOnItemClickListener(this);

        db.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
