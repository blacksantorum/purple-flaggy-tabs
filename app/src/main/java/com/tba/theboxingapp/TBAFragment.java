package com.tba.theboxingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class TBAFragment
        extends Fragment
        implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    protected RequestQueue mRequestQueue = TBAApplication.getInstance().getRequestQueue();
    protected ImageLoader imageLoader = TBAApplication.getInstance().getImageLoader();

    protected ProgressBar spinner;

    @Override
    public void onResponse(JSONObject response) {
        spinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        spinner.setVisibility(View.INVISIBLE);

        new AlertDialog.Builder(getActivity()).setTitle("Network error").
                setMessage(error.getLocalizedMessage()).setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing;
            }
        }).show();
    }
}
