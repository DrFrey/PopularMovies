package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchMoviesData().execute();

    }

    public class FetchMoviesData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return NetworkUtilities.getPopularMovies();
        }

        @Override
        protected void onPostExecute(String s) {
            TextView mTest = findViewById(R.id.tv_test);

            JsonUtilities.jsonExtract(s);
            mTest.setText("retrieved " + MovieList.getInstance().getMovieList().size() + " movies");
        }
    }
}