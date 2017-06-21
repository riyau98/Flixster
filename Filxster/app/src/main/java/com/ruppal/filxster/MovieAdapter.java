package com.ruppal.filxster;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruppal.filxster.models.Movie;

import java.util.ArrayList;

/**
 * Created by ruppal on 6/21/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    ArrayList<Movie> movies;
    public MovieAdapter(ArrayList<Movie> movies){
        this.movies=movies;
    }


    //creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the context and create the inflater
    }
    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }
    //returns total number of items in list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(View itemView) {
            super(itemView);
            //lookup view objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPoster);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview= (TextView) itemView.findViewById(R.id.tvOverview);
        }
    }

}
