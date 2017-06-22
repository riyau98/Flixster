package com.ruppal.filxster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruppal.filxster.models.Config;
import com.ruppal.filxster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    //api string constants
    public final static String API_BASE_URL= "https://api.themoviedb.org/3";
    public final static String API_KEY_PARAM ="api_key";
   //tag for logging from this activity
    public final static String TAG="MainActivity";

    AsyncHttpClient client;
    //list of movies
    ArrayList <Movie> movies;
    //the recycler view
    RecyclerView rvMovies;
    //the adapter wired to the recycler view
    MovieAdapter adapter;
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client=new AsyncHttpClient();
        movies=new ArrayList<>();
        //initialize the adapter
        adapter = new MovieAdapter(movies);
        //resolve the recycler view and connect a layout manager and the adapter
        rvMovies=(RecyclerView) findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);
        getConfiguration();



    }
    //get currently playing movies from movie api
    private void getCurrentMovies(){
        String url = API_BASE_URL + "/movie/now_playing";
        RequestParams params= new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.API_KEY));
        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            JSONArray results=response.getJSONArray("results");
                            for (int i =0 ; i<results.length();i++){
                                Movie curr_movie = new Movie(results.getJSONObject(i));
                                movies.add(curr_movie);
                                adapter.notifyItemInserted(movies.size()-1);
                            }
                            Log.i(TAG, String.format("Loaded %s movies", results.length()));

                        }
                        catch (JSONException e){
                            logErrors("Failed to parse movies now playing", e, true);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                        logErrors("Failed to get data from now playing api", throwable, true);
                    }
                }
        );

    }


    //sets values for base image url
    private void getConfiguration () {
        String url  = API_BASE_URL + "/configuration";
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.API_KEY));
        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response)  {
                        try {
                            config = new Config(response);
                            Log.i(TAG, String.format("Loaded configuration with image base url %s and " +
                                                     "poster size %s",
                                                     config.getImage_base_url(),
                                                     config.getPoster_size()));
                            //pass config to adapter
                            adapter.setConfig(config);
                            getCurrentMovies();
                        }
                        catch (JSONException e){
                            logErrors("Failed JSON parsing", e, true);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                        logErrors("Failed configuration", throwable, true);
                    }
                }
        );

    }

    //handle errors
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
