package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetails extends AppCompatActivity {
    private ImageView mMoviePosterLarge;
    private TextView mOriginalTitle;
    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        mMoviePosterLarge = findViewById(R.id.iv_poster_large);
        mOriginalTitle = findViewById(R.id.tv_movie_title);
        mOverview = findViewById(R.id.tv_overview);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mVoteAverage = findViewById(R.id.tv_vote_average);

        Movie movie = getIntent().getParcelableExtra("MovieDetails");

        String posterPath = movie.getPosterPath();
        String originalTitle = movie.getOriginalTitle();
        String overview = movie.getOverview();
        String releaseDate = movie.getReleaseDate();
        Double voteAverage = movie.getVoteAverage();

        mOriginalTitle.setText(originalTitle);
        mOverview.setText(overview);
        mVoteAverage.setText(voteAverage.toString());
        mReleaseDate.setText(releaseDate);
    }
}