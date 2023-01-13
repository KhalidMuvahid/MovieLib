package com.example.movielib.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movielib.MainActivity;
import com.example.movielib.R;
import com.example.movielib.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment implements CheckBox.OnCheckedChangeListener, TextWatcher {


    FragmentDetailBinding binding;
    Bundle bundle;

    public static DetailFragment newInstance(Bundle bundle) {

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = new Bundle();

        Bundle data = getArguments();
        binding.detailMovieName.setText(data.getString(MainActivity.MOVIE_NAME));
        binding.detailMovieDescription.setText(data.getString(MainActivity.MOVIE_DESC));
        binding.detailMovieImg.setImageResource(data.getInt(MainActivity.MOVIE_IMG));

        binding.detailLikeChB.setOnCheckedChangeListener(this);
        binding.detailCommentSectionEdt.addTextChangedListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,MainFragment.newInstance(bundle))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isChecked()){
            bundle.putBoolean("liked",true);
        }else{
            bundle.putBoolean("liked",false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String com = s.toString();
        bundle.putString("comment", com);
    }
}
