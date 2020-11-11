package com.example.musicplayer.Model;

import android.net.Uri;

public class Music {

    private Uri Path;
    private String Name;
    private String Album;
    private String Artist;
    private String Duration;

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public Uri getPath() {
        return Path;
    }

    public void setPath(Uri path) {
        Path = path;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

}
