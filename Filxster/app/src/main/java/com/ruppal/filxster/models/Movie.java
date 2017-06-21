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

    public Movie(JSONObject object) throws JSONException{
        title=object.getString("title");
        overview=object.getString("overview");
        posterPath = object.getString("poster_path");
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
}
