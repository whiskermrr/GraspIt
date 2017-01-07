package com.wiktorwolski.mrr.mobile_programming_final_project;


import android.app.Fragment;
import android.app.FragmentManager;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_challenge, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ChallengeHandler challengeHandler = new ChallengeHandler(getActivity(), null, null, 1);

        SQLiteDatabase db = challengeHandler.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM challenges", null);

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
        adapter.notifyDataSetChanged();
    }
}