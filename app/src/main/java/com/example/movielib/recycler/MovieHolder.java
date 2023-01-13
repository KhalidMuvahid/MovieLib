package com.example.movielib.recycler;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movielib.fragments.LovelyFragment;
import com.example.movielib.R;
import com.example.movielib.data.LovelyMovies;
import com.example.movielib.data.Movie;
import com.example.movielib.interfaces.RecyclerItemClickListener;

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

        if (listener instanceof LovelyFragment){
            detailBt.setVisibility(View.GONE);
            editBt.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_delete_outline_24));
        }


    }

    protected void bind(Movie movie){
        this.movie = movie;
        title.setText(movie.getName());
//        description.setText(movie.getDescription());

        Glide.with(itemView.getContext())
                .load(movie.getImage())
                .centerCrop()
                .error(R.drawable.ic_baseline_error_outline_24)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);

//        if (movie.getVisited()){
//            title.setTextColor(Color.GREEN);
//        }

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

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context,"sad",Toast.LENGTH_LONG).show();
                LovelyMovies.getInstance().addToLovelies(movie);
                return true;
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
