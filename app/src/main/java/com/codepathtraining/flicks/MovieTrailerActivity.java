package com.codepathtraining.flicks;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;

import static com.codepathtraining.flicks.MovieDetailsActivity.MOVIE_ID;
import static com.codepathtraining.flicks.MovieDetailsActivity.MOVIE_URL;


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

//        client = new AsyncHttpClient();


        youtube_api_key = "AIzaSyDECY_kSQ4b95Hra8-vw6WaS7HuMTS8o_U";

        int id = getIntent().getIntExtra(MOVIE_ID, 0);
        String url = getIntent().getStringExtra(MOVIE_URL);

        placeVideo(url);

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