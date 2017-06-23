package com.ruppal.filxster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruppal.filxster.models.Movie;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    Movie movie;
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
        setupOnClickListener();

    }

    private void setupOnClickListener() {
        ImageView img = (ImageView) findViewById(R.id.ivBackdropTrailer);
            img.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                    //create intent for new activity
                    Intent i = new Intent(this, MovieTrailerActivity.class);
                    //wrap the info of the movie in a parcel
                    i.putExtra("videoid", movie.getId());
                    //show the activity
                    startActivityForResult(i);
            }
        });
    }


}
