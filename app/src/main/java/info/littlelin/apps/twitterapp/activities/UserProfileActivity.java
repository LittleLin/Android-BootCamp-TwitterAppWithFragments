package info.littlelin.apps.twitterapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import info.littlelin.apps.twitterapp.R;
import info.littlelin.apps.twitterapp.TwitterApplication;
import info.littlelin.apps.twitterapp.models.User;

public class UserProfileActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        long userId = getIntent().getLongExtra("userId", 0l);
        this.loadProfileInfo(userId);
    }

    private void loadProfileInfo(long userId) {
        TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                User user = User.fromJson(json);
                getActionBar().setTitle("@" + user.getScreenName());

                populateProfileHeader(user);
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s);
            }
        }, userId);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }
}
