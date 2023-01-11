package com.example.movielib.data;

import com.example.movielib.R;

import java.util.ArrayList;

public class Data {

    private static ArrayList<Movie> movies = new ArrayList<>();
    private static Data data = null;

    private Data(){}

    public static Data getInstance(){
        if (data == null){
            data = new Data();
        }

        return data;
    }



    public  void addMovie(Movie movie){
        movies.add(movie);
    }

    public  ArrayList<Movie> movies(){
        return movies;
    }

    public  Movie getMovie(int index){
        return movies.get(index);
    }
}
