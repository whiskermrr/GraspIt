package com.wiktorwolski.mrr.mobile_programming_final_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private static EditText etFirstName;
    private static EditText etSurName;
    private static EditText etEmailRegistration;
    private static EditText etPasswordRegistration;
    UserHandler userHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = (EditText) findViewById(R.id.etFirstNameRegistration);
        etSurName = (EditText) findViewById(R.id.etSurNameRegistration);
        etEmailRegistration = (EditText) findViewById(R.id.etEmailRegistration);
        etPasswordRegistration = (EditText) findViewById(R.id.etPasswordRegistration);

        userHandler = new UserHandler(this, null, null, 1);
    }

    public void CreateUser(View v) {

        String userFirstName = etFirstName.getText().toString();
        String userSurName = etSurName.getText().toString();
        String userEmail = etEmailRegistration.getText().toString();
        String userPassword = etPasswordRegistration.getText().toString();

        if(userFirstName.matches("") || userSurName.matches("") || userEmail.matches("") || userPassword.matches("")) {

            Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(userFirstName, userSurName, userEmail, userPassword);
        userHandler.addUser(user);

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

        intent.putExtra("Registered", true);

        startActivity(intent);
    }
}
