package com.example.movielib.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movielib.MainActivity;
import com.example.movielib.MovieApplication;
import com.example.movielib.R;
import com.example.movielib.data.Data;
import com.example.movielib.data.Movie;
import com.example.movielib.data.MovieJson;
import com.example.movielib.databinding.FragmentMainBinding;
import com.example.movielib.recycler.MainRecycleAdapter;
import com.example.movielib.interfaces.RecyclerItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements RecyclerItemClickListener{

    private FragmentMainBinding binding;
    private MainRecycleAdapter recycleAdapter;
    private BottomSheetDialog dialog;



    public static MainFragment newInstance(Bundle bundle){
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dialog = new BottomSheetDialog(getActivity());
        if (getArguments() != null){
            System.out.println(getArguments().getBoolean("like"));
            System.out.println(getArguments().getString("comment"));
        }
        Data.getInstance().movies().clear();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater,container,false);
        recycleAdapter = new MainRecycleAdapter(Data.getInstance().movies(), this);
        binding.recyclerView.setAdapter(recycleAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.progressCircular.setVisibility(View.VISIBLE);
        MovieApplication.getInstance().movieService.getMovies().enqueue(new Callback<List<MovieJson>>() {
            @Override
            public void onResponse(Call<List<MovieJson>> call, Response<List<MovieJson>> response) {
                if (response.isSuccessful()){
                    List<MovieJson> movieJsonList = response.body();
                    for (MovieJson movieJson: movieJsonList){
                        Data.getInstance().addMovie(new Movie(movieJson));
                    }
                    recycleAdapter.notifyDataSetChanged();
                    binding.progressCircular.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<MovieJson>> call, Throwable t) {

            }

        });

        return binding.getRoot();
    }

    @Override
    public void onClickRecyclerItem(int position,int id) {

        switch (id){
            case R.id.editImBt:{
                openEditBottomSheet(position);
                break;
            }

            case R.id.detailBt:

            case R.id.movieImage: {
                openDetail(Data.getInstance().getMovie(position));
                break;
            }
        }

    }



    private void openEditBottomSheet(int position){
        View view = getLayoutInflater().inflate(R.layout.edit_bottom_sheet_dialog,null,false);
        dialog.setContentView(view);
        dialog.show();
    }

    private void openDetail(Movie movie){
//        movie.setVisited(true);
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.MOVIE_NAME,movie.getName());
//        bundle.putString(MainActivity.MOVIE_DESC,movie.getDescription());
//        bundle.putInt(MainActivity.MOVIE_IMG,movie.getImage());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, DetailFragment.newInstance(bundle))
                .addToBackStack(null)
                .commit();
    }




}
