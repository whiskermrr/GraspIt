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
import android.widget.AdapterView;
import android.widget.ListView;

public class ChallengeFragment extends Fragment implements FragmentManager.OnBackStackChangedListener, AdapterView.OnItemClickListener {

    ListView listOfChallenges;
    ChallengeAdapter adapter;
    SharedPreferences sharedPreferences;
    ChallengeHandler challengeHandler;
    private int userID;
    private int status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_challenge, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        challengeHandler = new ChallengeHandler(getActivity(), null, null, 1);

        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt(LoginActivity.USER_ID, -1);

        Cursor cursor = challengeHandler.getCursorOfChallengesOfLoggedUser(userID, status);
        adapter = new ChallengeAdapter(getActivity(), cursor, ChallengeFragment.this);

        listOfChallenges = (ListView) getActivity().findViewById(R.id.listOfChallenges);
        listOfChallenges.setAdapter(adapter);
        listOfChallenges.setOnItemClickListener(this);
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        adapter.selectedItem(position);
    }

    public void refreshDataInList() {

        adapter.getCursor().close();
        Cursor cursor = challengeHandler.getCursorOfChallengesOfLoggedUser(userID, status);
        adapter.swapCursor(cursor);
        adapter.notifyDataSetChanged();
    }



    public void challengeCheckAsDone(int id) {

        adapter.getCursor().close();
        challengeHandler.challengeCheckAsDone(id);
        Cursor cursor = challengeHandler.getCursorOfChallengesOfLoggedUser(userID, status);
        adapter.resetPosition();
        adapter.swapCursor(cursor);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}