package com.wiktorwolski.mrr.mobile_programming_final_project;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddChallengeFragment extends DialogFragment implements View.OnClickListener {

    ImageView iBrain;
    ImageView iBook;
    ImageView iWorkout;
    EditText tTitle;
    EditText tDescription;
    EditText tDeadline;
    Button bAdd;
    SharedPreferences sharedPreferences;
    Calendar myCalendar = Calendar.getInstance();
    int userID;
    boolean clicked;
    int choice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_challenge_fragment, null);

        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt(LoginActivity.USER_ID, -1);

        clicked = false;
        choice = -1;

        tTitle = (EditText) view.findViewById(R.id.tTitle);
        tDescription = (EditText) view.findViewById(R.id.tDescription);
        tDeadline = (EditText) view.findViewById(R.id.tDeadLine);

        tDeadline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        iBrain = (ImageView) view.findViewById(R.id.iBrain);
        iBook = (ImageView) view.findViewById(R.id.iBook);
        iWorkout = (ImageView) view.findViewById(R.id.iWorkout);

        bAdd = (Button) view.findViewById(R.id.bAdd);
        bAdd.setOnClickListener(this);

        iBook.setClickable(true);
        iBrain.setClickable(true);
        iWorkout.setClickable(true);

        iBrain.setOnClickListener(this);
        iBook.setOnClickListener(this);
        iWorkout.setOnClickListener(this);

        return view;
    }

    public void changeIconSize(View v, int choice, int dps) {

        ImageView image;

        switch (choice) {

            case 0:
                image = iBrain;
                break;

            case 1:
                image = iBook;
                break;

            case 2:
                image = iWorkout;
                break;

            default:
                return;
        }

        final float scale = getActivity().getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);

        android.view.ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
        layoutParams.width = pixels;
        layoutParams.height = pixels;
        image.setLayoutParams(layoutParams);
        Toast.makeText(v.getContext(), "Clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.bAdd) {

            addChallenge(v);
            ChallengeFragment listFragment = (ChallengeFragment) getFragmentManager().findFragmentByTag("A");

            listFragment.refreshDataInList();
            return;
        }

        if(clicked) {

            changeIconSize(v, this.choice, 50);
            this.choice = -1;
            clicked = false;
            return;
        }

        switch(v.getId()) {

            case R.id.iBrain:
                this.choice = 0;
                break;

            case R.id.iWorkout:
                this.choice = 2;
                break;

            case R.id.iBook:
                this.choice = 1;
                break;

            default:
                return;
        }

        changeIconSize(v, this.choice, 70);
        clicked = true;
    }

    public void addChallenge(View view) {

        String title = tTitle.getText().toString();
        String description = tDescription.getText().toString();
        String deadline = tDeadline.getText().toString();

        if(title.matches("") || deadline.matches("") || description.matches("") || this.choice == -1) {

            Toast.makeText(view.getContext(), "Please file all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        Challenge challenge = new Challenge();

        challenge.setTitle(title);
        challenge.setDescription(description);
        challenge.setDeadline(deadline);
        challenge.setIconId(this.choice);
        challenge.setIdOfUser(userID);
        challenge.setDone(false);
        ChallengeHandler challengeHandler = new ChallengeHandler(getActivity(), null, null, 1);
        challengeHandler.addChallenge(challenge);
        dismiss();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {

        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);

        tDeadline.setText(sdf.format(myCalendar.getTime()));
    }
}


