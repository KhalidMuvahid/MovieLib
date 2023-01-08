package com.example.movielib.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movielib.R;
import com.example.movielib.data.Movie;
import com.example.movielib.interfaces.RecyclerItemClickListener;

import java.util.List;

public class MainRecycleAdapter extends RecyclerView.Adapter<MovieHolder> {

    List<Movie> movies;
    RecyclerItemClickListener listener;

    public MainRecycleAdapter(List<Movie> movies,RecyclerItemClickListener listener){
        this.listener = listener;
        this.movies = movies;
    }

    public MainRecycleAdapter(List<Movie> movies){
        this.movies = movies;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.main_recycler_layout,parent,false);
        return new MovieHolder(view,parent.getContext(),listener);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
