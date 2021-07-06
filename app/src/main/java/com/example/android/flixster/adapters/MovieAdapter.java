package com.example.android.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.flixster.MovieDetailsActivity;
import com.example.android.flixster.R;
import com.example.android.flixster.databinding.ItemMovieBinding;
import com.example.android.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Activity activity;
    List<Movie> movies;

    public MovieAdapter(Activity activity, List<Movie> movies) {
        this.activity = activity;
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(activity.getLayoutInflater());
        View movieView = binding.getRoot();
        return new ViewHolder(movieView, binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final int POSTER_RADIUS = 30; // radius for rounded corners
        private static final int POSTER_MARGIN = 10; // crop margin for rounded corners

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(View itemView, ItemMovieBinding binding) {
            super(itemView);
            tvTitle = binding.tvTitle;
            tvOverview = binding.tvOverview;
            ivPoster = binding.ivPoster;
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            } else {
                imageUrl = movie.getPosterPath();
            }
            Glide.with(activity)
                    .load(imageUrl)
                    .centerInside()
                    .transform(new RoundedCornersTransformation(POSTER_RADIUS, POSTER_MARGIN))
                    .into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                Intent intent =  new Intent(activity, MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                activity.startActivity(intent);
            }
        }
    }
}
