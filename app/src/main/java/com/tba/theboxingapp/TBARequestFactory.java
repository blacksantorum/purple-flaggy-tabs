package com.tba.theboxingapp;

import android.location.GpsStatus;
import android.net.sip.SipSession;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tba.theboxingapp.Model.Comment;
import com.tba.theboxingapp.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

/**
 * Created by christibbs on 9/14/14.
 */

public class TBARequestFactory {

    private static final String BASE_URL = "http://www.theboxingapp.com/api/v2/";

    private static String withSessionToken(String url)
    {
        return url + "?session_token=" + User.currentUser().sessionToken;
    }

    private static JSONObject authObject()
    {
        JSONObject auth = new JSONObject();
        try {
            auth.put("session_token", User.currentUser().sessionToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auth;
    }

    public static JsonObjectRequest LoginRequest
            (JSONObject user, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        String url = BASE_URL + "signin";

        Log.i("url",url);

        return new JsonObjectRequest(url,user,listener,errorListener);
    }

    public static JsonArrayRequest PlacesRequest(Response.Listener<JSONArray> listener, Response.ErrorListener errorListener)
    {
        String url = BASE_URL + "places?session_token=";
        url += User.currentUser().sessionToken;

        Log.i("url",url);

        return new JsonArrayRequest(url,listener, errorListener);
    }

    public static JsonObjectRequest FeaturedFightsRequest(int page,
                                                          Response.Listener<JSONObject> listener,
                                                          Response.ErrorListener errorListener)
    {
        JSONObject params = new JSONObject();

        String url = BASE_URL + "fights/future?session_token="+User.currentUser()+"&page=" + String.valueOf(page);

        return new JsonObjectRequest(Request.Method.GET, url,params,listener,errorListener );
    }

    public static JsonObjectRequest PastFightsRequest(int page,
                                                          Response.Listener<JSONObject> listener,
                                                          Response.ErrorListener errorListener)
    {
        JSONObject params = new JSONObject();

        String url = BASE_URL + "fights/past?session_token="+User.currentUser()+"&page=" + String.valueOf(page);

        return new JsonObjectRequest(Request.Method.GET, url,params,listener,errorListener );
    }

    public static JsonArrayRequest FightsRequest(Response.Listener<JSONArray> listener,
                                                 boolean featured, Response.ErrorListener errorListener)
    {
        String url = BASE_URL + "fights/";

        if (featured) {
            url += "future";
        } else {
            url += "past";
        }

        url += "?session_token=" + User.currentUser().sessionToken;

        Log.i("url",url);

        return new JsonArrayRequest(url,listener, errorListener);
    }

    public static JsonObjectRequest PostCommentRequest(Response.Listener<JSONObject> listener, int fightId, String comment,
                                                       Response.ErrorListener errorListener)
    {
        String url = BASE_URL + "fights/" + fightId + "/comments";

        JSONObject params = new JSONObject();

        JSONObject commentObject = new JSONObject();
        try {
            commentObject.put("body", comment);
            params.put("comment", commentObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest(TBARequestFactory.withSessionToken(url),params,listener, errorListener);
    }

    public static JsonObjectRequest FightRequest(Response.Listener<JSONObject> listener, int fightId, Response.ErrorListener errorListener)
    {
        String url = BASE_URL + "fights/" + fightId + "?session_token=" + User.currentUser().sessionToken;

        return new JsonObjectRequest(url,null,listener, errorListener);
    }

    public static JsonArrayRequest CommentsRequest(Response.Listener<JSONArray> listener,
                                                   int fightId, Response.ErrorListener errorListener) {
        String url = BASE_URL + "fights/";

        url += fightId + "/comments?session_token=";
        url += User.currentUser().sessionToken;

        return new JsonArrayRequest(url,listener, errorListener);
    }

    public static StringRequest LikeRequest(Comment comment, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        String url = BASE_URL + "comments/" + comment.id + "/like";

        return new StringRequest(Request.Method.POST, withSessionToken(url),listener, errorListener);
    }

    public static JsonObjectRequest PickFightRequest(int fightId, int winnerId, Response.Listener<JSONObject> listener,
                                                     Response.ErrorListener errorListener)
    {
        String url = BASE_URL + "fights/" + fightId + "/picks";

        JSONObject params = new JSONObject();
        JSONObject pick = new JSONObject();
        try {
            pick.put("winner_id", "" + winnerId);
            pick.put("ko", "false");
            params.put("pick", pick);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest(withSessionToken(url),params,listener, errorListener);
    }

    public static JsonArrayRequest UserCommentsRequest(Response.Listener<JSONArray> listener,
                                                       int userId, Response.ErrorListener errorListener) {
        String url = BASE_URL + "users/" + userId + "/comments";

        return new JsonArrayRequest(withSessionToken(url),listener, errorListener);
    }

    public static JsonArrayRequest UserPicksRequest(Response.Listener<JSONArray> listener,
                                                    int userId, Response.ErrorListener errorListener) {
        String url = BASE_URL + "users/" + userId + "/picks";

        return new JsonArrayRequest(withSessionToken(url),listener, errorListener);
    }
}