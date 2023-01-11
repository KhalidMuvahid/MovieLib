package com.example.movielib.interfaces;

import com.example.movielib.data.MovieJson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {

    @GET("movies")
    Call<List<MovieJson>> getMovies();
}
