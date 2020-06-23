package com.example.android.popularmovies;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtilities {
    private final static String POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?api_key=";
    private final static String TOP_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=";

    //get this key from themoviedb.org
    private final static String API_KEY = "";

    public static String getPopularMovies(){
        URL url = popularMoviesUrl();
        String response = null;
        try {
            response = getResponseFromUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String getTopRatedMovies(){
        URL url = topRatedMoviesUrl();
        String response = null;
        try {
            response = getResponseFromUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static URL popularMoviesUrl() {
        String address = POPULAR_URL+API_KEY;
        URL url = null;
        try {
            Log.d("___MOV", address);

            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    };
    private static URL topRatedMoviesUrl(){
        String address = TOP_URL+API_KEY;
        URL url = null;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    };

    private static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            Log.d("___MOV", "opening connection");

            InputStream stream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
