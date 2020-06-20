package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_movie_list);
        mErrorMessage = findViewById(R.id.tv_error_message);



        mProgressBar = findViewById(R.id.pb_loading);
        new FetchMoviesData().execute();

    }

    private void showErrorMessage() {

    }
    public class FetchMoviesData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return NetworkUtilities.getPopularMovies();
        }

        @Override
        protected void onPostExecute(String s) {
            JsonUtilities.jsonExtract(s);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}