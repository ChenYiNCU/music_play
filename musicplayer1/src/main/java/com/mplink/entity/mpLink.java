package com.mplink.entity;

public class mpLink {
    private Playlist playlist=new Playlist();
    private Music music =new Music();

    public mpLink() {
    }

    public mpLink(Playlist playlist, Music music) {
        this.playlist = playlist;
        this.music = music;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
