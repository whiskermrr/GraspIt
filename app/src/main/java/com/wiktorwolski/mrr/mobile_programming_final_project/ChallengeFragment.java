package com.wiktorwolski.mrr.mobile_programming_final_project;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_challenge, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ChallengeHandler challengeHandler = new ChallengeHandler(getActivity(), null, null, 1);

        SQLiteDatabase db = challengeHandler.getWritableDatabase();

        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        int id = sharedPreferences.getInt(LoginActivity.USER_ID, -1);
        Cursor cursor = db.rawQuery("SELECT * FROM challenges WHERE owner_id = ?", new String[] {Integer.toString(id)});

        adapter = new ChallengeAdapter(getActivity(), cursor);

        listOfChallenges = (ListView) getActivity().findViewById(R.id.listtt);
        listOfChallenges.setAdapter(adapter);
        listOfChallenges.setOnItemClickListener(this);

        db.close();
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        adapter.selectedItem(position);
        listOfChallenges.invalidateViews();
        adapter.notifyDataSetChanged();
    }
}