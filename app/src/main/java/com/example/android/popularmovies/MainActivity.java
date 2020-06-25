package com.example.android.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private MovieAdapter mMovieAdapter;
    private int noOfColumns;
    private int resizedImageWidth;
    private final int IMAGE_WIDTH = 342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_movie_list);
        mErrorMessage = findViewById(R.id.tv_error_message);
        mProgressBar = findViewById(R.id.pb_loading);

        calculateDimensions(this);

        GridLayoutManager layoutManager =
                new GridLayoutManager(this, noOfColumns);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this, resizedImageWidth);
        mRecyclerView.setAdapter(mMovieAdapter);

        showMovies();

        new FetchMoviesData().execute(1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_item_popular) {
            new FetchMoviesData().execute(1);
            return true;
        } else if(id == R.id.menu_item_top) {
            new FetchMoviesData().execute(2);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMovies() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(String error) {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
        mErrorMessage.setText(error);
    }
    private void calculateDimensions(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        noOfColumns = screenWidth / IMAGE_WIDTH;

        int margin = screenWidth - IMAGE_WIDTH * noOfColumns;
        resizedImageWidth = IMAGE_WIDTH + margin / noOfColumns;
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, MovieDetails.class);
        intent.putExtra("MovieDetails", movie);
        startActivity(intent);
    }

    public class FetchMoviesData extends AsyncTask<Integer, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... params) {
            if(NetworkUtilities.isOnline()) {
                if (params[0] == 1) {
                    return NetworkUtilities.getPopularMovies();
                } else if (params[0] == 2) {
                    return NetworkUtilities.getTopRatedMovies();
                } else {
                    return "Oops... Something is wrong...";
                }
            } else {
                return "No internet :(((";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if(s != null && s.startsWith("{")) {
                JsonUtilities.jsonExtract(s);
                if(MovieList.getInstance().getMovieList() != null) {
                    mMovieAdapter.notifyDataSetChanged();
                } else {
                    showErrorMessage("Movie list is null :(");
                }
            } else {
                showErrorMessage(s);
            }
        }
    }
}