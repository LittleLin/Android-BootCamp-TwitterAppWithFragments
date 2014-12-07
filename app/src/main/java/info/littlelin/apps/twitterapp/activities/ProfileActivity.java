package info.littlelin.apps.twitterapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class ProfileActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        tvFollowers.setText(user.getFriendsCount() + " Following");

        ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
