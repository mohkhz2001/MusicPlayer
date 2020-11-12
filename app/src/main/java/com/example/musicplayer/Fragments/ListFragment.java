package com.example.musicplayer.Fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.Adapter.MusicAdapter;
import com.example.musicplayer.Model.Music;
import com.example.musicplayer.R;

import java.util.ArrayList;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;


public class ListFragment extends Fragment {

    ArrayList<Music> musicList;
    MediaPlayer mediaPlayer;


    private TextView txt_music_name, txt_artist_name, txt_time;
    private SeekBar seekBar;
    private ImageView img_play_stop, img_music;
    private LinearLayout linear;
    double currentPos;

    View view;
    RecyclerView recyclerView;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);
        musicList = new ArrayList<>();
        InitViews();
        ViewsController();
        if (checkPermission()) {
            Log.e("permission", " " + checkPermission());
            getAudioFiles();
        }

        return view;
    }


    private void InitViews() {
        recyclerView = view.findViewById(R.id.recyclerView);
        txt_music_name = view.findViewById(R.id.txt_music_name);
        txt_artist_name = view.findViewById(R.id.txt_artist_name);
        txt_time = view.findViewById(R.id.txt_time);
        seekBar = view.findViewById(R.id.seekbar);
        img_play_stop = view.findViewById(R.id.img_play_stop);
        img_music = view.findViewById(R.id.img_music);
        linear = view.findViewById(R.id.linear);

        mediaPlayer = new MediaPlayer();


    }

    private void ViewsController() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentPos = seekBar.getProgress();
                mediaPlayer.seekTo((int) currentPos);
            }
        });

        img_play_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    Toast.makeText(getContext(), "choose the music", Toast.LENGTH_SHORT).show();
                } else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    img_play_stop.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                } else {
                    mediaPlayer.start();
                    img_play_stop.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                }
            }
        });

    }

    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {

                    }
                }
            }
        }
    }

    //fetch the audio files from storage
    public void getAudioFiles() {

        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        //looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                Music modelAudio = new Music();

                modelAudio.setArtist(artist);
                modelAudio.setPath(Uri.parse(url));
                modelAudio.setName(title);
                modelAudio.setDuration(duration);

                musicList.add(modelAudio);

            } while (cursor.moveToNext());
        }

        Log.e("CLICK", " " + musicList.size());
        MusicAdapter adapter = new MusicAdapter(getContext(), musicList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(int pos, View v) {
                Log.e("Click", " " + pos);
                ClickOnTheMusic(pos);
                timerConversion(pos);
            }
        });

    }

    private void PlayAudio(int pos) {
        try {
            mediaPlayer.reset();
            mediaPlayer.reset();


            String a = musicList.get(pos).getPath().getPath();

            //set file path
            Log.e("ERROR", "1" + a);

            mediaPlayer.setDataSource(getContext(), musicList.get(pos).getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            img_play_stop.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);


        } catch (Exception e) {
            e.printStackTrace();
        }
        setAudioProgress();
    }

    //set audio progress
    public void setAudioProgress() {
        //get the audio duration
        currentPos = mediaPlayer.getCurrentPosition();
        int total_duration = mediaPlayer.getDuration();

        //display the audio duration
//        total.setText(timerConversion((long) total_duration));
//        current.setText(timerConversion((long) current_pos));
        seekBar.setMax((int) total_duration);
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    currentPos = mediaPlayer.getCurrentPosition();
//                    current.setText(timerConversion((long) current_pos));
                    seekBar.setProgress((int) currentPos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    //set the time to the txt
    private void timerConversion(int pos) {

        Long time = Long.valueOf(musicList.get(pos).getDuration());
        int mns = (int) ((time / 60000) % 60000);
        int scs = (int) (time % 60000 / 1000);
        Log.e("TIME", " " + mns + ":" + scs);

        txt_time.setText(mns + ":" + scs);

    }

    private void ClickOnTheMusic(int pos) {


        Log.e("CLICK", " " + pos);

        txt_music_name.setText(musicList.get(pos).getName().toString());
        txt_artist_name.setText(musicList.get(pos).getArtist().toString());
        txt_time.setText(musicList.get(pos).getDuration().toString());

        linear.setVisibility(View.VISIBLE);
        PlayAudio(pos);///
    }

}