package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {
    private ImageView mMoviePosterLarge;
    private TextView mOriginalTitle;
    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mVoteAverage;
    private TextView mMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        mMovieTitle = findViewById(R.id.tv_movie_title);
        mMoviePosterLarge = findViewById(R.id.iv_poster_large);
        mOriginalTitle = findViewById(R.id.tv_original_title);
        mOverview = findViewById(R.id.tv_overview);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mVoteAverage = findViewById(R.id.tv_vote_average);

        Movie movie = getIntent().getParcelableExtra("MovieDetails");

        String posterPath = movie.getPosterPath();
        String originalTitle = "Original title: " + movie.getOriginalTitle();
        String overview = movie.getOverview();
        String releaseDate = "Released on: " + movie.getReleaseDate();
        String voteAverage = "Rating: " + movie.getVoteAverage().toString() + " / 10";
        String movieTitle = movie.getTitle();

        String posterURL = "http://image.tmdb.org/t/p/w500" + posterPath;
        Picasso.get().load(posterURL).into(mMoviePosterLarge);

        mMovieTitle.setText(movieTitle);
        mOriginalTitle.setText(originalTitle);
        mOverview.setText(overview);
        mVoteAverage.setText(voteAverage);
        mReleaseDate.setText(releaseDate);
    }
}