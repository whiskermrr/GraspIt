package com.wiktorwolski.mrr.mobile_programming_final_project;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class UserProfileFragment extends Fragment implements FragmentManager.OnBackStackChangedListener, View.OnClickListener {

    TextView tUserFullName;
    TextView tUserEmail;
    EditText etUserProfilelOldPassword;
    EditText etUserProfilelNewPassword;
    Button bChangePassword;
    UserHandler userHandler;
    SharedPreferences sharedPreferences;
    private int userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tUserFullName = (TextView) getActivity().findViewById(R.id.tUserFullName);
        tUserEmail = (TextView) getActivity().findViewById(R.id.tUserEmail);
        etUserProfilelOldPassword = (EditText) getActivity().findViewById(R.id.etUserProfilelOldPassword);
        etUserProfilelNewPassword = (EditText) getActivity().findViewById(R.id.etUserProfilelNewPassword);
        bChangePassword = (Button) getActivity().findViewById(R.id.bChangePassword);

        bChangePassword.setOnClickListener(this);

        userHandler = new UserHandler(getActivity(), null, null, 1);

        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt(LoginActivity.USER_ID, -1);

        Cursor cursor = userHandler.getCursorOfLoggedUser(userID);

        if(cursor != null) {

            cursor.moveToFirst();
            tUserFullName.setText(cursor.getString(cursor.getColumnIndexOrThrow("firstname")) + " " +
            cursor.getString(cursor.getColumnIndexOrThrow("lastname")));
            tUserEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
        }
    }

    public void changePassword() {

        if(userHandler.changePassword(etUserProfilelOldPassword.getText().toString(), etUserProfilelNewPassword.getText().toString(), userID)) {

            Toast.makeText(getActivity(), "Password Changed!", Toast.LENGTH_SHORT).show();
            etUserProfilelOldPassword.setText("");
            etUserProfilelNewPassword.setText("");
        }
        else {

            Toast.makeText(getActivity(), "Wrong old password!", Toast.LENGTH_SHORT).show();
            etUserProfilelOldPassword.setText("");
            etUserProfilelNewPassword.setText("");
        }
    }

    @Override
    public void onClick(View v) {

        changePassword();
    }

    @Override
    public void onBackStackChanged() {

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
