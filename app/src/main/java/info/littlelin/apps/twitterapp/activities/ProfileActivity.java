package info.littlelin.apps.twitterapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import info.littlelin.apps.twitterapp.R;
import info.littlelin.apps.twitterapp.TwitterApplication;
import info.littlelin.apps.twitterapp.fragments.UserTimelienFragment;
import info.littlelin.apps.twitterapp.models.User;

public class ProfileActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Replace the container with the new fragment\
        UserTimelienFragment fragmentUserTimeline = new UserTimelienFragment();
        Bundle args = new Bundle();
        args.putInt("userId", 0);
        fragmentUserTimeline.setArguments(args);

        ft.replace(R.id.flUserTimeline, fragmentUserTimeline);

        // Execute the changes specified
        ft.commit();

        this.loadProfileInfo();
    }

    private void loadProfileInfo() {
        TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                User user = User.fromJson(json);
                getActionBar().setTitle("@" + user.getScreenName());

                populateProfileHeader(user);
            }
        });
    }

    private void populateProfileHeader(User user) {
        // fine views' references
        TextView tvName = (TextView) this.findViewById(R.id.tvName);
        TextView tvTagline = (TextView) this.findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) this.findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) this.findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) this.findViewById(R.id.ivProfileImage);

        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFriendsCount() + " Following");

        ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
    }
}
