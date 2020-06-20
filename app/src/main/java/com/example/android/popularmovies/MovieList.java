package com.example.android.popularmovies;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    private static MovieList instance = null;
    private List<Movie> mMovieList;


    private MovieList() {
        mMovieList = new ArrayList<Movie>();
    }

    public static MovieList getInstance(){
        if(instance == null) {
            instance = new MovieList();
        }
        return instance;
    }

    public List<Movie> getMovieList() {
        return this.mMovieList;
    }

    public void addItem(Movie movie){
        mMovieList.add(movie);
    }
}
