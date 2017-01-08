package com.wiktorwolski.mrr.mobile_programming_final_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    UserHandler userHandler;
    EditText etLoginEmail;
    EditText etLoginPassword;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "userInfo" ;
    public static final String USER_ID = "userId";
    public static final String LOGGED = "logged";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        boolean logged = sharedPreferences.getBoolean(LOGGED, false);

        if(logged) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        userHandler = new UserHandler(this, null, null, 1);
    }

    public void signIn(View v) {

        if(userHandler.signIn(etLoginEmail.getText().toString(), etLoginPassword.getText().toString())) {

            Toast.makeText(this, "Logged In!", Toast.LENGTH_SHORT).show();

            int id = userHandler.getLoggedUserId(etLoginEmail.getText().toString());

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(USER_ID, id);
            editor.putBoolean(LOGGED, true);
            editor.apply();

            etLoginEmail.setText("");
            etLoginPassword.setText("");

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        else {

            Toast.makeText(this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
            etLoginEmail.setText("");
            etLoginPassword.setText("");
        }
    }

    public void goToRegister(View v) {

        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
