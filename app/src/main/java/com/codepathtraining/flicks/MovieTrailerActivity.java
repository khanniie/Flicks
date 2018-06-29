package com.codepathtraining.flicks;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.codepathtraining.flicks.MovieDetailsActivity.MOVIE_ID;


public class MovieTrailerActivity extends YouTubeBaseActivity {

    public Integer id;
    public String youtube_api_key;
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    public final static String API_KEY_PARAM = "api_key";
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        client = new AsyncHttpClient();


        youtube_api_key = "AIzaSyDECY_kSQ4b95Hra8-vw6WaS7HuMTS8o_U";

        int id = getIntent().getIntExtra(MOVIE_ID, 0);
        Toast.makeText(getApplicationContext(), id + "", Toast.LENGTH_LONG).show();
        getVideoUrl(id + "");
    }

    private String getVideoUrl(String id) {
        //create the url
        String url = API_BASE_URL + "/movie/" + id +"/videos";
        //set request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        //execute GET request expecting a JSON object

        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //load results into movies list
                try {
                    JSONArray results = response.getJSONArray("results");

                    Log.i("TRAILER", "" + results.length());
                    if(results.length() < 1){
                        return;
                    }
                    else{
                        placeVideo(results.getJSONObject(0).getString("key"));
                    }
                } catch (JSONException e) {
                    Log.e("LMAO YOU FAILED", "LMAO");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("LMAO YOU FAILED", "LMAO");
            }
        });
        return "";
    }

    private void placeVideo(String url){
        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);
        final String video_url = url;

        // initialize with API key stored in secrets.xml
        playerView.initialize(youtube_api_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(video_url);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}