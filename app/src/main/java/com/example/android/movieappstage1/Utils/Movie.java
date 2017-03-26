package com.example.android.movieappstage1.Utils;

/**
 * Created by mina essam on 26-Mar-17.
 */

public class Movie {
    private String title,description, posterPath,date;
    private float vote;
    public Movie(){}
    public Movie(String title,String description,float vote,String posterPath,String date){
        this.title=title;
        this.description=description;
        this.vote=vote;
        this.posterPath = posterPath;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getVote() {
        return vote;
    }

    public void setVote(float vote) {
        this.vote = vote;
    }
}
