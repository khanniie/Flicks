package com.codepathtraining.flicks.models;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import cz.msebera.android.httpclient.Header;

@Parcel
public class Movie {

    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    public final static String API_KEY_PARAM = "api_key";

    //values from API
    private String title;
    private String overview;
    private String posterpath; //only the path
    private String backdropPath;
    private Double voteAverage;
    private Integer id;
    private String url;


    public Movie() {}

    //init from JSON data
    public Movie(JSONObject object, String key) throws JSONException{
        title = object.getString("title");
        overview = object.getString("overview");
        posterpath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        voteAverage = object.getDouble("vote_average");
        id = object.getInt("id");
        getVideoUrl(id + "", key);
    }

    // going to make calls to video endpoint here, because calling it in MovieListActivity might result in a null url
    // when object is constructed if
    //the api call hasn't returned yet.
    private void getVideoUrl(String id, String key) {
        AsyncHttpClient client = new AsyncHttpClient();
        //create the url
        String url = API_BASE_URL + "/movie/" + id +"/videos";
        //set request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, key);
        //execute GET request expecting a JSON object
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //load results into movies list
                try {
                    String url = "NONE";
                    JSONArray results = response.getJSONArray("results");
                    for(int i = 0; i < results.length(); i ++){
                        if(results.getJSONObject(i).getString("site").equals("YouTube")){
                            url = results.getJSONObject(i).getString("key");
                            break;
                        }
                    }
                    setUrl(url);

                } catch (JSONException e) {
                    Log.e("MOVIE_CLASS", "couldn't get video url from JSON when making movie class!");
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("MOVIE_CLASS", "couldn't get JSON when making movie class!");
            }
        });
    }

    private void setUrl(String url){
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterpath;
    }
    public String getBackdropPath(){
        return backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getHasValidVideo() {
        return !(url.equals("NONE"));
    }
    public String getUrl() {
        return url;
    }
}
