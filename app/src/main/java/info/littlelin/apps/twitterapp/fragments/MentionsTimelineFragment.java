package info.littlelin.apps.twitterapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import info.littlelin.apps.twitterapp.R;
import info.littlelin.apps.twitterapp.TwitterApplication;
import info.littlelin.apps.twitterapp.TwitterClient;
import info.littlelin.apps.twitterapp.models.Tweet;

public class MentionsTimelineFragment extends TweetsListFragment {
    public static final int PAGE_SIZE = 5;
    private TwitterClient client;

    private Long currentMaxId = 1l;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.client = TwitterApplication.getRestClient();
        this.populateTime(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ListView lvTweets = (ListView) view.findViewById(R.id.lvTweets);
//        lvTweets.setOnScrollListener(new EndlessScrollListener(PAGE_SIZE - 1, 0) {
//            private long lastScrollingMaxId1;
//
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                if (page > 8) {
//                    return;
//                }
//
//                this.loadMoreTweets(page);
//            }
//
//            private void loadMoreTweets(int page) {
//                populateTime(currentMaxId - 1);
//            }
//        });

        return view;
    }

    private void populateTime(long maxId) {
        this.client.getMentionsTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                ArrayList<Tweet> freshTweets = Tweet.fromJson(json);
                currentMaxId = freshTweets.get(freshTweets.size() - 1).getId();
                addAll(freshTweets);
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s);
            }
        }, maxId);
    }
}
