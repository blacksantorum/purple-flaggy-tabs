package com.tba.theboxingapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.tba.theboxingapp.Model.Fight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FightCardsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FightCardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FightCardsFragment extends TBAFragment {

    public enum FightCardsType {FEATURED, PAST};

    // the fragment initialization parameters
    private static final String TYPE_PARAM = "type";
    private RecyclerView mRecyclerView;
    private FightCardsAdapter mAdapter;

    private int page = 1;
    private int totalFights;

    private FightCardsType mType;

   // private OnFragmentInteractionListener mListener;

    public static FightCardsFragment newInstance(FightCardsType type) {
        FightCardsFragment fragment = new FightCardsFragment();
        Bundle args = new Bundle();
        args.putSerializable(TYPE_PARAM, type);
        fragment.setArguments(args);
        return fragment;
    }

    public FightCardsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = (FightCardsType)getArguments().getSerializable(TYPE_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fight_cards, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.fightList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        spinner = (ProgressBar)v.findViewById(R.id.fightListSpinner);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FightCardsAdapter(R.layout.fight_card_layout, getActivity(), this);
        mRecyclerView.setAdapter(mAdapter);

        fetchFights();

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    private void fetchFights()
    {
        if (page == 1) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.VISIBLE);
        }
        if (mType == FightCardsType.FEATURED) {
            mRequestQueue.add(TBARequestFactory.FeaturedFightsRequest(page, this, this));
        } else {
            mRequestQueue.add(TBARequestFactory.PastFightsRequest(page, this, this));
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            totalFights = response.getInt("fights_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray fightObjects = response.getJSONArray("fights");
            for (int i = 0; i < fightObjects.length(); i++) {
                // JSONObject fightObject = fightObjects.getJSONObject(i).getJSONObject("fight");
                Fight fight = new Fight(fightObjects.getJSONObject(i));
                mAdapter.fights.add(fight);
            }
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (page == 1) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        super.onResponse(response);
        page++;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
