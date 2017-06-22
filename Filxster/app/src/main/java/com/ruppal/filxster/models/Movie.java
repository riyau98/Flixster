package com.ruppal.filxster.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ruppal on 6/21/17.
 */

public class Movie  {
    //values from API
    private String title;
    private String overview;
    //not the full url!
    private String posterPath;
    private String backdropPath;

    public Movie(JSONObject object) throws JSONException{
        title=object.getString("title");
        overview=object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath=object.getString("backdrop_path");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
}
