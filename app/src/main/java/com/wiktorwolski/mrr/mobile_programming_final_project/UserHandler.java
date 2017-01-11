package com.wiktorwolski.mrr.mobile_programming_final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_IMAGE = "image";

    public UserHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_FIRSTNAME + " TEXT NOT NULL," +
                COLUMN_LASTNAME + " TEXT NOT NULL," +
                COLUMN_EMAIL + " TEXT NOT NULL," +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_IMAGE + " BLOB " +
                ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public Cursor getCursorOfLoggedUser(int id) {

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_ID + " = ?", new String[] {Integer.toString(id)});

        return cursor;
    }

    public boolean changePassword(String oldPassword, String newPassword, int userID) {

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_ID + " = ?", new String[] {Integer.toString(userID)});

        boolean changed = false;

        if(cursor != null) {

            cursor.moveToFirst();
            String passwordStored = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));

            if(passwordStored.matches(oldPassword)) {

                cursor.close();
                ContentValues values = new ContentValues();
                values.put(COLUMN_PASSWORD, newPassword);
                db.update(TABLE_USERS, values, "id = " + Integer.toString(userID), null);
                db.close();

                changed = true;
                return changed;
            }

            else {
                cursor.close();
                db.close();
                return changed;
            }
        }

        return changed;
    }

    public int getLoggedUserId(String email) {

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[] {email});
        cursor.moveToLast();
        int id = cursor.getInt(cursor.getColumnIndex("id"));

        return id;
    }

    public void addUser(User user) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRSTNAME, user.getFirstName());
        values.put(COLUMN_LASTNAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        db.insert(TABLE_USERS, null, values);

        Cursor cursor = db.query(TABLE_USERS,
                new String[] {
                        COLUMN_ID,
                        COLUMN_FIRSTNAME,
                        COLUMN_LASTNAME,
                        COLUMN_EMAIL,
                        COLUMN_PASSWORD
                }
                ,null, null, null, null, null);

        cursor.moveToLast();

        int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        user.setId(id);

        cursor.close();
        db.close();
    }

    public boolean signIn(String userLogin, String password) {

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?";

        Cursor cursor = db.rawQuery(query, new String[] {userLogin});

        boolean passwordMatches = false;

        if(cursor.moveToFirst()) {

            String storedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

            if(password.matches(storedPassword)) {
                passwordMatches = true;
            }
        }

        cursor.close();
        db.close();

        return passwordMatches;
    }

    public void addImage(byte[] image, int id) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, image);
        db.update(TABLE_USERS, values, "id = " + Integer.toString(id), null);
        db.close();
    }

    public byte[] getUserImage(int userId) {

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE " + COLUMN_ID + " = ?", new String[] {Integer.toString(userId)});

        cursor.moveToFirst();

        byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));

        return image;
    }
}
