package com.wiktorwolski.mrr.mobile_programming_final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class ChallengeHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "challenges.db";
    private static final String TABLE_CHALLENGES = "challenges";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DEADLINE = "deadline";
    private static final String COLUMN_ICON_ID = "icon_id";
    private static final String COLUMN_OWNER_ID = "owner_id";
    private static final String COLUMN_STATUS = "status";


    public ChallengeHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_CHALLENGES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                COLUMN_DEADLINE + " TEXT NOT NULL," +
                COLUMN_ICON_ID + " INTEGER NOT NULL, " +
                COLUMN_OWNER_ID + " INTEGER NOT NULL, " +
                COLUMN_STATUS + " INTEGER DEFAULT 0 " +
                ");";

        db.execSQL(query);
    }

    public void challengeCheckAsDone(int id) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_STATUS, 1);

        db.update(TABLE_CHALLENGES, values, "_id = " + Integer.toString(id), null);

        db.close();
    }

    public Cursor getCursorOfChallengesOfLoggedUser(int id) {

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CHALLENGES + " WHERE " +
                COLUMN_OWNER_ID + " = ? AND " + COLUMN_STATUS + " = 0", new String[] {Integer.toString(id)});

        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHALLENGES);
        onCreate(db);
    }

    public void addChallenge(Challenge challenge) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_OWNER_ID, challenge.getIdOfUser());
        values.put(COLUMN_TITLE, challenge.getTitle());
        values.put(COLUMN_DESCRIPTION, challenge.getDescription());
        values.put(COLUMN_DEADLINE, challenge.getDeadline());
        values.put(COLUMN_ICON_ID, challenge.getIconId());

        db.insert(TABLE_CHALLENGES, null, values);
        db.close();
    }
}
