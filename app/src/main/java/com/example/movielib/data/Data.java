package com.example.movielib.data;

import com.example.movielib.R;

import java.util.ArrayList;

public class Data {

    private static ArrayList<Movie> movies = new ArrayList<>();
    private static Data data = null;

    private Data(){
        movies.add(new Movie("The Boy", "This is a good movie about teenager boy and his friends.They are have a lot of adventure.", R.drawable.boy, false));
        movies.add(new Movie("The Old Woman", "This is a good movie about old woman and how she spend her time alon.This is a sad story prepare napkins.", R.drawable.grandma, false));
        movies.add(new Movie("The Dog", "This is a good movie about a dog and how hard his live.This is a sad story prepare napkins", R.drawable.dog, false));
    }

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
