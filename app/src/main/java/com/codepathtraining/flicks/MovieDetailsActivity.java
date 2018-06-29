package com.codepathtraining.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepathtraining.flicks.models.Config;
import com.codepathtraining.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final int EDIT_REQUEST_CODE = 20;
    // keys used for passing data between activities
    public static final String MOVIE_ID = "movieId";
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
    @BindView(R.id.playVideo) ImageView playVid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        // resolve the view objects
        ButterKnife.bind(this);

        client = new AsyncHttpClient();

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        getConfiguration();
    }

    private void setImage(){
        String imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());
        int placeholderId = R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = (ImageView) findViewById(R.id.playVideo);
        Glide.with(this).load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 25, 0))
                .placeholder(placeholderId).
                error(placeholderId).
                into(imageView);
    }

    @OnClick(R.id.playVideo)
    public void playVideo(){
        Log.i("Testing the thing", "" + movie.getId());
        Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
        // put "extras" into the bundle for access in the edit activity
        i.putExtra(MOVIE_ID, movie.getId());
        // brings up the edit activity with the expectation of a result
        startActivity(i);
    }

    private void getConfiguration() {
        //create URL
        String url = API_BASE_URL + "/configuration";
        //set request param
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key)); //API
        //execute GET request expecting a JSON obj response
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    config = new Config(response);

                    //get now playing movies
                    setImage();
                } catch (JSONException e) {
                    logError("Failed parsing configuration", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration", throwable, true);
            }
        });
    }
    //handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUser) {
        //always log the error
        Log.e("Details page", message, error);
        if(alertUser){
            //show a long toast with error message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

}