package com.example.movielib.recycler;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movielib.DetailActivity;
import com.example.movielib.MainActivity;
import com.example.movielib.R;
import com.example.movielib.data.Movie;

public class MovieHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private ImageView imageView;
    private ImageButton editBt;
    private Button detailBt;
    private Context context;
    RecyclerItemClickListener listener;
    private Movie movie;

    public MovieHolder(View itemView, Context context,RecyclerItemClickListener listener) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        title = itemView.findViewById(R.id.movieName);
        description = itemView.findViewById(R.id.movieDescription);
        imageView = itemView.findViewById(R.id.movieImage);
        detailBt = itemView.findViewById(R.id.detailBt);
        editBt = itemView.findViewById(R.id.editImBt);


    }

    protected void bind(Movie movie){
        this.movie = movie;
        title.setText(movie.getName());
        description.setText(movie.getDescription());
        imageView.setImageResource(movie.getImage());

        if (movie.getVisited()){
            title.setTextColor(Color.GREEN);
        }

        detailBt.setOnClickListener(v -> {
            listener.onClickRecyclerItem(getAdapterPosition(),v.getId());
        });

        editBt.setOnClickListener(v->{
            listener.onClickRecyclerItem(getAdapterPosition(),v.getId());
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewAnimation(v);
            }
        });
    }

    private void imageViewAnimation(View v){
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView,"rotation",360);

        AnimatorSet set = new AnimatorSet();
        set.play(rotation);
        set.setDuration(1000);
        set.setInterpolator(new DecelerateInterpolator());
        set.start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setRotation(0);
                listener.onClickRecyclerItem(getAdapterPosition(),v.getId());
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
