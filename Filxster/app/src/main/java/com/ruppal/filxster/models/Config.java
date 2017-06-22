package com.ruppal.filxster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ruppal on 6/22/17.
 */

public class Config {
    String image_base_url;
    String poster_size;
    String backdrop_size;

    public Config (JSONObject object) throws JSONException {
        JSONObject images=object.getJSONObject("images");
        image_base_url = images.getString("secure_base_url");
        JSONArray poster_size_array=images.getJSONArray("poster_sizes");
        poster_size=poster_size_array.optString(3, "w342");
        //parse the backdrop size
        JSONArray backdrop_size_array=images.getJSONArray("backdrop_sizes");
        backdrop_size=backdrop_size_array.optString(1, "w780");
    }
    //helper to construct the whole url
    public String getImageUrl(String size, String path){
        //not using poster size in case we want to use different sizes later on
        return String.format("%s%s%s", image_base_url, size, path); //concatenate
    }

    public String getImage_base_url() {
        return image_base_url;
    }

    public String getPoster_size() {
        return poster_size;
    }

    public String getBackdrop_size() {
        return backdrop_size;
    }
}
