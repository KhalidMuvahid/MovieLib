package com.example.movielib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielib.data.LovelyMovies;
import com.example.movielib.interfaces.RecyclerItemClickListener;
import com.example.movielib.recycler.CustomItemAnimator;
import com.example.movielib.recycler.MainRecycleAdapter;

public class LovelyFragment extends Fragment implements RecyclerItemClickListener {

    MainRecycleAdapter recycler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lovely,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.loveliesRecycler);
        recycler = new MainRecycleAdapter(LovelyMovies.getInstance().getLovelyMovies(),this);
        recyclerView.setAdapter(recycler);
        recyclerView.setItemAnimator(new CustomItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Button addButton = view.findViewById(R.id.add_lovely_movie);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClickRecyclerItem(int position, int id) {
        switch (id){
            case R.id.editImBt:{
                LovelyMovies.getInstance().removeFromLovelies(position);
                recycler.notifyItemRemoved(position);
                break;
            }
        }
    }
}
