package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private final String IMAGE_SIZE = "w342";

    private final MovieAdapterOnClickHandler mClickHandler;
    private int mImageWidth;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, int imageWidth) {
        mClickHandler = clickHandler;
        mImageWidth = imageWidth;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {

        Movie movie = MovieList.getInstance().getMovieList().get(position);
        String coverImageAddress = BASE_IMAGE_URL + IMAGE_SIZE + movie.getPosterPath();
        Picasso.get().load(coverImageAddress).into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        if(MovieList.getInstance().getMovieList().size() == 0) {
            return 0;
        } else {
            return MovieList.getInstance().getMovieList().size();
        }
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mImageView;

        public MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_movie_cover);
            mImageView.getLayoutParams().width = mImageWidth;
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAbsoluteAdapterPosition();
            Movie movie = MovieList.getInstance().getMovieList().get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }
}
