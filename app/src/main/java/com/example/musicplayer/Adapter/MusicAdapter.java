package com.example.musicplayer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Model.Music;
import com.example.musicplayer.R;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.viewHolder> {

    Context context;
    ArrayList<Music> musicList;
    public OnItemClickListener onItemClickListener;

    public MusicAdapter(Context context, ArrayList<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MusicAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_list , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.viewHolder holder, int position) {
        holder.artist_name_txt.setText(musicList.get(position).getArtist());
        holder.music_name_txt.setText(musicList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class  viewHolder extends RecyclerView.ViewHolder{
        TextView music_name_txt, artist_name_txt;

        public viewHolder(View itemView) {

            super(itemView);
            music_name_txt = (TextView) itemView.findViewById(R.id.Music_Name_txt);
            artist_name_txt = (TextView) itemView.findViewById(R.id.Artist_Name_TXT);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), v);/////
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }
}