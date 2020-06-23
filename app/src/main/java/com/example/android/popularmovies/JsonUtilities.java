package com.example.android.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public final class JsonUtilities {
    private static final String POPULARITY = "popularity";
    private static final String VOTE_COUNT = "vote_count";
    private static final String VIDEO = "video";
    private static final String POSTER_PATH = "poster_path";
    private static final String ID = "id";
    private static final String ADULT = "adult";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String GENRE_IDS = "genre_ids";
    private static final String TITLE = "title";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";


    public static void jsonExtract(String jsonString){

        MovieList.getInstance().getMovieList().clear();
        Log.d("___MOV", jsonString);


        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray results = jsonObject.getJSONArray("results");
            for(int i = 0; i < results.length(); i++) {
                Movie movie = new Movie();
                JSONObject movieItem =  results.getJSONObject(i);

                movie.setPopularity(movieItem.getDouble(POPULARITY));
                movie.setVoteCount(movieItem.getInt(VOTE_COUNT));
                movie.setVideo(movieItem.getBoolean(VIDEO));
                movie.setPosterPath(movieItem.getString(POSTER_PATH));
                movie.setId(movieItem.getInt(ID));
                movie.setAdult(movieItem.getBoolean(ADULT));
                movie.setBackdropPath(movieItem.getString(BACKDROP_PATH));
                movie.setOriginalLanguage(movieItem.getString(ORIGINAL_LANGUAGE));
                movie.setOriginalTitle(movieItem.getString(ORIGINAL_TITLE));
                movie.setTitle(movieItem.getString(TITLE));
                movie.setVoteAverage(movieItem.getDouble(VOTE_AVERAGE));
                movie.setOverview(movieItem.getString(OVERVIEW));
                movie.setReleaseDate(movieItem.getString(RELEASE_DATE));

                JSONArray genreIds = movieItem.getJSONArray(GENRE_IDS);
                int [] ids = new int[genreIds.length()];
                for(int k = 0; k < genreIds.length(); k++) {
                    ids[k] = genreIds.optInt(k);
                }
                movie.setGenreIds(ids);

                MovieList.getInstance().addItem(movie);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
