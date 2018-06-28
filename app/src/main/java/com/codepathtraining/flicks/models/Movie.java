package com.codepathtraining.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Movie {
    //values from API
    private String title;
    private String overview;
    private String posterpath; //only the path
    private String backdropPath;
    Double voteAverage;

    public Movie() {}

    //init from JSON data
    public Movie(JSONObject object) throws JSONException{
        title = object.getString("title");
        overview = object.getString("overview");
        posterpath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        voteAverage = object.getDouble("vote_average");
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
}
