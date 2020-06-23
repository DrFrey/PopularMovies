package com.example.android.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_movie_list);
        mErrorMessage = findViewById(R.id.tv_error_message);
        mProgressBar = findViewById(R.id.pb_loading);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this);


        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter();
        mRecyclerView.setAdapter(mMovieAdapter);

        showMovies();
        new FetchMoviesData().execute(1);
        Log.d("___MOV", "initial create");

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

    private void showErrorMessage() {
        Log.d("___MOV", "error");
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }


    public class FetchMoviesData extends AsyncTask<Integer, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... params) {
            if (params[0] == 1) {
                Log.d("___MOV", "pop selected");
                return NetworkUtilities.getPopularMovies();
            } else if(params[0] == 2) {
                Log.d("___MOV", "top selected");
                return NetworkUtilities.getTopRatedMovies();
            } else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if(s != null) {
                JsonUtilities.jsonExtract(s);
                if(MovieList.getInstance().getMovieList() != null) {
                    Log.d("___MOV", "retrieved" + MovieList.getInstance().getMovieList().size());

                    mMovieAdapter.notifyDataSetChanged();
                } else {
                    showErrorMessage();
                }
            } else {
                showErrorMessage();
            }

        }
    }
}