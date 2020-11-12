package com.example.musicplayer.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Model.Music;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.viewHolder> {
    @NonNull
    @Override
    public MusicAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class  viewHolder extends RecyclerView.ViewHolder{


        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
