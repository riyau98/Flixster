package com.ruppal.filxster;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruppal.filxster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;
import static com.ruppal.filxster.MainActivity.API_BASE_URL;
import static com.ruppal.filxster.MainActivity.API_KEY_PARAM;

public class MovieTrailerActivity extends YouTubeBaseActivity {
    AsyncHttpClient client;
    String trailerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);
        client=new AsyncHttpClient();
        // temporary test video id -- TODO replace with movie trailer video id
        final String videoId = "tKodtNFpzBA";

        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        //initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.YOUTUBE_API_KEY), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }


    //get movie trailer link from movie api
    private void getTrailerLink(Movie movie){
        String url = API_BASE_URL + String.format("/movie/%s/videos", movie.getId());
        RequestParams params= new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.API_KEY));
        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            JSONArray results=response.getJSONArray("results");
                            trailerLink="";
                            if (results.length()>0){
                                trailerLink = (results.getJSONObject(0)).getString("id");
                            }

                            Log.i(TAG, String.format("Trailer: %s", trailerLink));

                        }
                        catch (JSONException e){
                            logErrors("Failed to parse trailer", e, true);
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                        logErrors("Failed to get data from videos api", throwable, true);
                    }
                }
        );
    }

    private void logErrors (String message, Throwable error, boolean showUser){
        //always log the error
        Log.e(TAG, message, error);
        //alert user
        if (showUser){
            //use a toast
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

    }
}
