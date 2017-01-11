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

    private int position = -1;
    int status;
    ChallengeFragment parentFragment;

    public static class ChallengeViewHolder {

        ImageView challengeIcon;
        TextView challengeTitle;
        TextView challengeDeadline;
        TextView challengeDescription;
        Button bFinished;
        int challengeId;
    }

    private static final int[] challengeIcons = {

            R.drawable.brain_icon,
            R.drawable.book_icon,
            R.drawable.workout_icon
    };

    ChallengeAdapter(Context context, Cursor cursor, ChallengeFragment parentFragment) {

        super(context, cursor, 0);
        this.parentFragment = parentFragment;
        this.status = parentFragment.getStatus();
    }

    public void selectedItem(int position) {

        if(this.position == position) {

            this.position = -1;
        }

        else {

            this.position = position;
        }

        notifyDataSetChanged();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        ChallengeViewHolder holder = new ChallengeViewHolder();

        View view = LayoutInflater.from(context).inflate(R.layout.extended_row_challenge_list, parent, false);
        holder.challengeIcon = (ImageView) view.findViewById(R.id.ChallengeIconExtended);
        holder.challengeTitle = (TextView) view.findViewById(R.id.ChallengeTitleExtended);
        holder.challengeDeadline = (TextView) view.findViewById(R.id.ChallengeDeadlineExtended);
        holder.challengeDescription = (TextView) view.findViewById(R.id.ChallengeDescriptionExtended);
        holder.bFinished = (Button) view.findViewById(R.id.bFinished);

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        final ChallengeViewHolder holder = (ChallengeViewHolder) view.getTag();

        holder.challengeTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));
        holder.challengeDeadline.setText(cursor.getString(cursor.getColumnIndexOrThrow("deadline")));
        holder.challengeIcon.setImageResource(challengeIcons[cursor.getInt(cursor.getColumnIndexOrThrow("icon_id"))]);
        holder.challengeDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow("description")));
        holder.challengeId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

        holder.bFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parentFragment.challengeCheckAsDone(holder.challengeId);
                notifyDataSetChanged();
            }
        });

        if(this.position != cursor.getPosition()) {

            holder.challengeDescription.setVisibility(view.GONE);
            holder.bFinished.setVisibility(view.GONE);
        }

        else if(status == 0){

            holder.challengeDescription.setVisibility(view.VISIBLE);
            holder.bFinished.setVisibility(view.VISIBLE);
        }

        else if(status == 1) {

            holder.challengeDescription.setVisibility(view.VISIBLE);
        }
    }

    public void resetPosition() {

        this.position = -1;
    }
}
