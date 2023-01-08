package com.example.movielib.data;

import java.util.ArrayList;

public class LovelyMovies {

    private static LovelyMovies instance = null;

    private static ArrayList<Movie> lovelies = new ArrayList<Movie>();

    private LovelyMovies(){}


    public static LovelyMovies getInstance(){
        if (instance == null){
            instance = new LovelyMovies();
        }
        return instance;
    }

    public  ArrayList<Movie> getLovelyMovies(){
        return  lovelies;
    }

    public  void addToLovelies(Movie movie){
        lovelies.add(movie);
    }

    public  void removeFromLovelies(int position){
        lovelies.remove(position);
    }


}
