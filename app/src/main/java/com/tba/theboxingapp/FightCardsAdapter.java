package com.tba.theboxingapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.tba.theboxingapp.Model.Fight;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by christibbs on 12/6/14.
 */
public class FightCardsAdapter extends RecyclerView.Adapter<FightCardsAdapter.ViewHolder> {

    public List<Fight> fights;
    private int rowLayout;
    private Context mContext;
    private TBAFragment mFragment;

    public FightCardsAdapter(int rowLayout, Context context, TBAFragment fragment)
    {
        this.fights = new ArrayList<Fight>();
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.mFragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Fight fight = fights.get(i);
        viewHolder.dateLabel.setText(new SimpleDateFormat("MMMM d").format(fight.date));
        viewHolder.boxerALabel.setText(fight.boxerA.fullName);
        viewHolder.boxerBLabel.setText(fight.boxerB.fullName);
        viewHolder.boxerAPicture.setImageUrl(fight.boxerA.imgUrl, mFragment.imageLoader);
        viewHolder.boxerBPicture.setImageUrl(fight.boxerB.imgUrl, mFragment.imageLoader);

        viewHolder.pickBar.setProgress(50);

        if (fight.state == Fight.State.IN_PROGRESS) {
            viewHolder.pickBar.setThumb(mFragment.getResources().getDrawable(R.drawable.vs_button));
            viewHolder.pickBar.setEnabled(false);

        } else if (fight.state == Fight.State.PAST) {
            if (fight.stoppage) {
                viewHolder.pickBar.setThumb(mFragment.getResources().getDrawable(R.drawable.ko_button));
            } else {
                viewHolder.pickBar.setThumb(mFragment.getResources().getDrawable(R.drawable.def_button));
            }
            viewHolder.pickBar.setEnabled(false);
        } else {
            viewHolder.pickBar.setThumb(mFragment.getResources().getDrawable(R.drawable.vs_button));
            viewHolder.pickBar.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return fights == null ? 0 : fights.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateLabel;
        public TextView boxerALabel;
        public TextView boxerBLabel;
        public NetworkImageView boxerAPicture;
        public NetworkImageView boxerBPicture;
        public SeekBar pickBar;

        public ViewHolder(View itemView) {
            super(itemView);
            dateLabel = (TextView) itemView.findViewById(R.id.dateLabel);
            boxerALabel = (TextView)itemView.findViewById(R.id.boxerALabel);
            boxerBLabel = (TextView)itemView.findViewById(R.id.boxerBLabel);
            boxerAPicture = (NetworkImageView)itemView.findViewById(R.id.boxerAPicture);
            boxerBPicture = (NetworkImageView)itemView.findViewById(R.id.boxerBPicture);
            pickBar = (SeekBar)itemView.findViewById(R.id.pickBar);

           // int width = boxerAPicture.getLayoutParams().width;
           // boxerAPicture.getLayoutParams().height = boxerBPicture.getLayoutParams().height = width;
        }

    }
}
