package com.wiktorwolski.mrr.mobile_programming_final_project;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ChallengeAdapter extends CursorAdapter {

    private int position = 1;

    public static class ChallengeViewHolder {

        ImageView challengeIcon;
        TextView challengeTitle;
        TextView challengeDeadline;
        TextView challengeDescription;
        Button bFinished;
        Button bRemove;

    }

    public static final int[] challengeIcons = {

            R.drawable.brain_icon,
            R.drawable.book_icon,
            R.drawable.workout_icon
    };

    public ChallengeAdapter(Context context, Cursor cursor) {

        super(context, cursor, 0);
    }

    public void selectedItem(int position) {

        this.position = position;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        ChallengeViewHolder holder = new ChallengeViewHolder();

        if(this.position == cursor.getPosition()) {

            View view2 = LayoutInflater.from(context).inflate(R.layout.extended_row_challenge_list, parent, false);
            holder.challengeIcon = (ImageView) view2.findViewById(R.id.ChallengeIconExtended);
            holder.challengeTitle = (TextView) view2.findViewById(R.id.ChallengeTitleExtended);
            holder.challengeDeadline = (TextView) view2.findViewById(R.id.ChallengeDeadlineExtended);
            holder.challengeDescription = (TextView) view2.findViewById(R.id.ChallengeDescriptionExtended);
            holder.bFinished = (Button) view2.findViewById(R.id.bFinished);
            holder.bRemove = (Button) view2.findViewById(R.id.bRemove);

            view2.setTag(holder);

            return view2;
        }


        View view = LayoutInflater.from(context).inflate(R.layout.row_challenge_list, parent, false);
        holder.challengeIcon = (ImageView) view.findViewById(R.id.ChallengeIcon);
        holder.challengeTitle = (TextView) view.findViewById(R.id.ChallengeTitle);
        holder.challengeDeadline = (TextView) view.findViewById(R.id.ChallengeDeadline);

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ChallengeViewHolder holder = (ChallengeViewHolder) view.getTag();

        holder.challengeTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));
        holder.challengeDeadline.setText(cursor.getString(cursor.getColumnIndexOrThrow("deadline")));
        holder.challengeIcon.setImageResource(challengeIcons[cursor.getInt(cursor.getColumnIndexOrThrow("icon_id"))]);
    }
}
