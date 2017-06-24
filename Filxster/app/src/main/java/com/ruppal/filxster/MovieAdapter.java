package com.ruppal.filxster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruppal.filxster.models.Config;
import com.ruppal.filxster.models.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by ruppal on 6/21/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{


    ArrayList<Movie> movies;
    //needed for image urls
    Config config;
    boolean isPortrait;
    Context context;
    public MovieAdapter(ArrayList<Movie> movies){
        this.movies=movies;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }
    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());
        //determine current orientation
        isPortrait =  context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        //build url using config based on orientation
        String image_url = null;

        if (isPortrait) {
            image_url = config.getImageUrl(config.getPoster_size(), movie.getPosterPath());
            String backdrop_url = config.getImageUrl(config.getBackdrop_size(), movie.getBackdropPath());
            movie.setImageUrl(backdrop_url);

        }
        else{
            image_url = config.getImageUrl(config.getBackdrop_size(), movie.getBackdropPath());
            movie.setImageUrl(image_url);
        }

        //get the correct placeholder based on orientation
        int placeholderId= isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView= isPortrait ? holder.ivPosterImage : holder.ivBackdropImage;
        //use glide to load photos
        int radius = 20; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
        Glide.with(context)
                .load(image_url)
                .bitmapTransform(new RoundedCornersTransformation(context, radius, margin))
                .placeholder(placeholderId)
                .error(placeholderId)
                .into(imageView);
    }
    //returns total number of items in list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivBackdropImage;

        public ViewHolder(View itemView) {
            super(itemView);
            //lookup view objects by id
            //one of the following two images could be null based on which orientation you are in
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPoster);
            ivBackdropImage=(ImageView) itemView.findViewById(R.id.ivBackdropImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview= (TextView) itemView.findViewById(R.id.tvOverview);
            itemView.setOnClickListener(this);
        }
        //when user clicks on a row, show movie details
        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                Movie movie= movies.get(position);
                //create intent for new activity
                Intent i = new Intent(context, DetailsActivity.class);
                //wrap the info of the movie in a parcel
                i.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                //show the activity
                context.startActivity(i);
            }
        }
    }

}