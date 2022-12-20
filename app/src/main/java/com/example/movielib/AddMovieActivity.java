package com.example.movielib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.movielib.data.Data;
import com.example.movielib.data.Movie;

public class AddMovieActivity extends AppCompatActivity{

    EditText movieName;
    EditText movieDescription;
    Button saveBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);



        movieName = findViewById(R.id.new_movie_name);
        movieDescription = findViewById(R.id.new_movie_description);
        saveBt = findViewById(R.id.addNewMovieBt);

        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = movieName.getText().toString();
                String description = movieDescription.getText().toString();
                Data.getInstance().addMovie(new Movie(name,description,R.drawable.ic_baseline_image_24,false));
                finish();
            }
        });
    }


}