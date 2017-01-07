package com.wiktorwolski.mrr.mobile_programming_final_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static UserHandler userHandler;
    private static EditText etLoginEmail;
    private static EditText etLoginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);

        userHandler = new UserHandler(this, null, null, 1);
    }

    public void signIn(View v) {

        if(userHandler.signIn(etLoginEmail.getText().toString(), etLoginPassword.getText().toString())) {

            Toast.makeText(this, "Logged In!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userName", etLoginEmail.getText().toString());

            etLoginEmail.setText("");
            etLoginPassword.setText("");

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
