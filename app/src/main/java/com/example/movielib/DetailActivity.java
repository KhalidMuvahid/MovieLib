package com.example.movielib;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailActivity extends AppCompatActivity implements CheckBox.OnCheckedChangeListener, TextWatcher {

    CheckBox likeIt;
    EditText comment;
    TextView movie_name;
    TextView movie_description;
    ImageView movie_image;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       likeIt = findViewById(R.id.likeChB);
       movie_name = findViewById(R.id.movie_name);
       movie_description = findViewById(R.id.movie_description);
        ImageView movie_image= findViewById(R.id.movie_img);

       Bundle bundle = getIntent().getExtras();
       movie_name.setText(bundle.getString(MainActivity.MOVIE_NAME));
       movie_description.setText(bundle.getString(MainActivity.MOVIE_DESC));
       movie_image.setImageResource(bundle.getInt(MainActivity.MOVIE_IMG));

       comment = findViewById(R.id.commentSectionEdt);
       likeIt.setOnCheckedChangeListener(this);
       comment.addTextChangedListener(this);


       intent = new Intent();
       setResult(RESULT_OK,intent);
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isChecked()){
            intent.putExtra("liked",true);
        }else{
            intent.putExtra("liked",false);
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
        intent.putExtra("comment", com);
    }
}