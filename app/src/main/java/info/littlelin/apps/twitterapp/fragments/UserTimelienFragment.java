package info.littlelin.apps.twitterapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import info.littlelin.apps.twitterapp.R;
import info.littlelin.apps.twitterapp.TwitterApplication;
import info.littlelin.apps.twitterapp.TwitterClient;
import info.littlelin.apps.twitterapp.models.Tweet;

public class UserTimelienFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.client = TwitterApplication.getRestClient();

        long userId = getArguments().getLong("userId");
        this.populateTime(0, userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ListView lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        return view;
    }

    private void populateTime(long maxId, long userId) {
        this.client.getUserTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                ArrayList<Tweet> freshTweets = Tweet.fromJson(json);
                addAll(freshTweets);
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s);
            }
        }, userId);
    }
}
