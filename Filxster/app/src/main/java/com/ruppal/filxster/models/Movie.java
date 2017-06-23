package com.ruppal.filxster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


/**
 * Created by ruppal on 6/21/17.
 */

@Parcel //class is parcelable
public class Movie  {
    //values from API
    String title;
    String overview;
    //not the full url!
    String posterPath;
    String backdropPath;
    Double vote_average;
    //movie id
    Integer id;
    String imageUrl;

    public Movie(){} //required for parceler

    public Movie(JSONObject object) throws JSONException{
        title=object.getString("title");
        overview=object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath=object.getString("backdrop_path");
        vote_average=object.getDouble("vote_average");
        id = object.getInt("id");
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
    public Double getVote_average() {
        return vote_average;
    }

    public Integer getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
