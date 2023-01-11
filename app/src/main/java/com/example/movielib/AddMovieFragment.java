package com.example.movielib;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movielib.data.Data;
import com.example.movielib.data.Movie;
import com.example.movielib.databinding.FregmentAddMovieBinding;

public class AddMovieFragment extends Fragment {

    FregmentAddMovieBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FregmentAddMovieBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addNewMovieBt.setOnClickListener(v -> {
            String name = binding.newMovieName.getText().toString();
            String description = binding.newMovieDescription.getText().toString();
//            Data.getInstance().addMovie(new Movie(name,description,R.drawable.ic_baseline_image_24,false));
            closeKeyBoard();
            getActivity().onBackPressed();
        });
    }

    private void closeKeyBoard() {
        View view = getActivity().getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
