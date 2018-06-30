package com.codepathtraining.flicks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepathtraining.flicks.models.Config;
import com.codepathtraining.flicks.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    // keys used for passing data between activities
    public static final String MOVIE_ID = "movieId";
    public static final String MOVIE_URL = "movieUrl";
    private String youtube_api_key;
    //constants
    //the base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    public final static String API_KEY_PARAM = "api_key";

    // the movie to display
    Movie movie;
    //image config
    Config config;
    AsyncHttpClient client;

    // the view objects
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.rbRating) RatingBar rbVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        // resolve the view objects
        ButterKnife.bind(this);

        client = new AsyncHttpClient();
        youtube_api_key = getString(R.string.youtube_api_key);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        config = (Config) Parcels.unwrap(getIntent().getParcelableExtra(Config.class.getSimpleName()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
        if(movie.getHasValidVideo()){
            placeVideo(movie.getUrl());
        }
        else{
            placeImage();
        }
    }

    private void placeVideo(String url){
        findViewById(R.id.ivBackdropImage).setVisibility(View.GONE);
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

    private void placeImage(){
        findViewById(R.id.player).setVisibility(View.GONE);
        String imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());
        int placeholderId = R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = (ImageView) findViewById(R.id.ivBackdropImage);
        Glide.with(this).load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 25, 0))
                .placeholder(placeholderId).
                error(placeholderId).
                into(imageView);
    }

}


//    private void setImage(){
//        String imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());
//        int placeholderId = R.drawable.flicks_backdrop_placeholder;
//        ImageView imageView = (ImageView) findViewById(R.id.playVideo);
//        Glide.with(this).load(imageUrl)
//                .bitmapTransform(new RoundedCornersTransformation(this, 25, 0))
//                .placeholder(placeholderId).
//                error(placeholderId).
//                into(imageView);
//    }
//
//
//    //    @OnClick(R.id.playVideo)
//    public void playVideo(){
//        Log.i("Testing the thing", "" + movie.getId());
//        Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
//        // put "extras" into the bundle for access in the edit activity
//        i.putExtra(MOVIE_ID, movie.getId());
//        i.putExtra(MOVIE_URL, movie.getUrl());
//        // brings up the edit activity with the expectation of a result
//        startActivity(i);
//    }
//
//    private void getConfiguration() {
//        //create URL
//        String url = API_BASE_URL + "/configuration";
//        //set request param
//        RequestParams params = new RequestParams();
//        params.put(API_KEY_PARAM, getString(R.string.api_key)); //API
//        //execute GET request expecting a JSON obj response
//        client.get(url, params, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    config = new Config(response);
//
//                    //get now playing movies
////                    setImage();
//                } catch (JSONException e) {
//                    logError("Failed parsing configuration", e, true);
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                logError("Failed getting configuration", throwable, true);
//            }
//        });
//    }
//    //handle errors, log and alert user
//    private void logError(String message, Throwable error, boolean alertUser) {
//        //always log the error
//        Log.e("Details page", message, error);
//        if(alertUser){
//            //show a long toast with error message
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//        }
//    }