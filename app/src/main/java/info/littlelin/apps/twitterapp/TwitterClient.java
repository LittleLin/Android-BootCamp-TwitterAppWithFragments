package info.littlelin.apps.twitterapp;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import info.littlelin.apps.twitterapp.fragments.HomeTimelineFragment;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "IAYqcWyFIdG0YRFMToUfD43mF";       // Change this
    public static final String REST_CONSUMER_SECRET = "VQM4EetKy955eyncjkCVn8YeOI53rvvriKRNvY03MTpDHwltjk"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cptwitterapp"; // Change this (here and in manifest)

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getAccountInformation(AsyncHttpResponseHandler handler, String screenName) {
        String apiUrl = this.getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);

        this.client.get(apiUrl, params, handler);
    }

    public void getAccountScreenName(AsyncHttpResponseHandler handler) {
        String apiUrl = this.getApiUrl("account/settings.json");
        this.client.get(apiUrl, null, handler);
    }

    public void getHomeTimeline(AsyncHttpResponseHandler handler, long maxId) {
        String apiUrl = this.getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();

        if (maxId != 0) {
            params.put("max_id", String.valueOf(maxId));
        }
        params.put("count", String.valueOf(HomeTimelineFragment.PAGE_SIZE));

        this.client.get(apiUrl, params, handler);
    }

    public void getMentionsTimeline(JsonHttpResponseHandler handler, long maxId) {
        String apiUrl = this.getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();

        if (maxId != 0) {
            params.put("max_id", String.valueOf(maxId));
        }
        params.put("count", String.valueOf(HomeTimelineFragment.PAGE_SIZE));

        this.client.get(apiUrl, params, handler);
    }

    public void postTweet(String body, AsyncHttpResponseHandler handler) {
        String apiUrl = this.getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", "test");

        this.client.post(apiUrl, params, handler);
    }

    public void getMyInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = this.getApiUrl("account/verify_credentials.json");
        this.client.get(apiUrl, null, handler);
    }

    public void getUserInfo(AsyncHttpResponseHandler handler, long userId) {
        String apiUrl = this.getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("user_id", String.valueOf(userId));
        this.client.get(apiUrl, params, handler);
    }

    public void getUserTimeline(AsyncHttpResponseHandler handler, long userId) {
        String apiUrl = this.getApiUrl("statuses/user_timeline.json");

        if (userId == 0) {
            this.client.get(apiUrl, null, handler);
        } else {
            RequestParams params = new RequestParams();
            params.put("user_id", String.valueOf(userId));
            this.client.get(apiUrl, params, handler);
        }
    }

    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    /*
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}
    */
	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}