package com.codepathtraining.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    //values from API
    private String title;
    private String overview;
    private String posterpath; //only the path

    //init from JSON data
    public Movie(JSONObject object) throws JSONException{
        title = object.getString("title");
        overview = object.getString("overview");
        posterpath = object.getString("poster_path");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterpath() {
        return posterpath;
    }
}
