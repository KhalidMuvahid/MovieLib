package com.example.movielib.recycler;

import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielib.R;

public class CustomItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        holder.itemView.setAnimation(
                AnimationUtils.loadAnimation(
                        holder.itemView.getContext(),
                        R.anim.slide_in));
        return super.animateRemove(holder);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        holder.itemView.setAnimation(
                        AnimationUtils.loadAnimation(
                        holder.itemView.getContext(),
                        R.anim.view_holder_add_anim));
        return super.animateAdd(holder);
    }

    @Override
    public long getRemoveDuration() {
        return 500;
    }
}
