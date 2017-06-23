package com.ruppal.filxster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruppal.filxster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

import static com.ruppal.filxster.MainActivity.API_BASE_URL;
import static com.ruppal.filxster.MainActivity.API_KEY_PARAM;

public class DetailsActivity extends AppCompatActivity {

    Movie movie;
    AsyncHttpClient client;
    TextView tvTitleDetail;
    TextView tvOverviewDetail;
    ImageView ivPosterDetail;
    RatingBar rbRating;
    String trailerLink;
    //    Context context;
//    Config config;
    String TAG = "MovieTrailer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        client=new AsyncHttpClient();
        //Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));
        tvTitleDetail = (TextView) findViewById(R.id.tvTitleDetail);
        tvOverviewDetail = (TextView) findViewById(R.id.tvOverviewDetail);
        rbRating = (RatingBar) findViewById(R.id.rbRating);
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        ivPosterDetail = (ImageView) findViewById(R.id.ivBackdropTrailer);
        Glide.with(this)
                .load(movie.getImageUrl())
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .error(R.drawable.flicks_backdrop_placeholder)
                .into(ivPosterDetail);

        //load overview
        tvOverviewDetail.setText(movie.getOverview());
        //load title
        tvTitleDetail.setText(movie.getTitle());
        float voteAverage = movie.getVote_average().floatValue();
        rbRating.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
        getTrailerLink(movie);
    }

    private void setupOnClickListener() {
        ImageView img = (ImageView) findViewById(R.id.ivBackdropTrailer);
            img.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                    //create intent for new activity
                    Intent i = new Intent(DetailsActivity.this, MovieTrailerActivity.class);
                    //wrap the info of the movie in a parcel
                    i.putExtra("videoid", trailerLink);
                    //show the activity
                    startActivity(i);
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
                                trailerLink = (results.getJSONObject(0)).getString("key");
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
        setupOnClickListener();

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
