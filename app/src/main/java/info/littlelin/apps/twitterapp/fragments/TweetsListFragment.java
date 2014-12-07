package info.littlelin.apps.twitterapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import info.littlelin.apps.twitterapp.R;
import info.littlelin.apps.twitterapp.adapters.TweetArrayAdapter;
import info.littlelin.apps.twitterapp.models.Tweet;

public class TweetsListFragment extends Fragment {
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;
    private ListView lvTweets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.tweets = new ArrayList<Tweet>();
        this.aTweets = new TweetArrayAdapter(this.getActivity(), tweets);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);

        // Assign our view reference
        this.lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        this.lvTweets.setAdapter(this.aTweets);


        // Return the layout view
        return v;
    }

    // return the adapter to the activity
    public TweetArrayAdapter getAdapter() {
        return this.aTweets;
    }

    // delegate the adding to the internal adapter
    public void addAll(ArrayList<Tweet> tweets) {
        if (tweets == null || tweets.size() == 0) {
            return;
        }

        this.aTweets.addAll(tweets);
    }
}
