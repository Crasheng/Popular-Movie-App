package com.example.ahmad.popularmovies_final.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ahmad on 7/9/2015.
 * This Class Represents Each Movie Data, And its Object Can be treated as Parcelable object
 */
public class MovieData implements Parcelable {

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {


        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
    private int movie_id;
    private String movie_title;
    private String movie_poster;
    private String movie_overview;
    private String movie_backdrop;
    private float movie_ave_vote;
    private String movie_release_date;

    private String movie_original_title;

    public MovieData() {
    }

    private MovieData(Parcel in) {
        movie_id = in.readInt();
        movie_title = in.readString();
        movie_poster = in.readString();
        movie_ave_vote = in.readFloat();
        movie_overview = in.readString();
        movie_backdrop = in.readString();
        movie_release_date = in.readString();
        movie_original_title = in.readString();
    }

    public MovieData set_id(int id) {
        this.movie_id = id;
        return this;
    }

    public MovieData title(String t) {
        this.movie_title = t;
        return this;
    }

    public MovieData originaltitle(String ot) {
        this.movie_original_title = ot;
        return this;
    }

    public MovieData overview(String ov) {
        this.movie_overview = ov;
        return this;
    }

    public MovieData backdropPath(String bd) {
        this.movie_backdrop = bd;
        return this;
    }

    public MovieData posterPath(String pp) {
        this.movie_poster = pp;
        return this;
    }

    public MovieData releaseDate(String rd) {
        this.movie_release_date = rd;
        return this;
    }

    public MovieData averagevote(float vote_average) {
        this.movie_ave_vote = vote_average;
        return this;
    }

    public String getMovieOriginalTitle() {
        return movie_original_title;
    }

    public String getMovieTitle() {
        return this.movie_title;
    }

    public String getMovieBackdrop() {
        return movie_backdrop;
    }

    public String getMoviePoster() {
        return movie_poster;
    }

    public String getMovieOverview() {
        return movie_overview;
    }

    public String getMovieReleaseDate() {
        return movie_release_date;
    }

    public float getMovieRating() {
        return movie_ave_vote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movie_id);
        dest.writeString(movie_title);
        dest.writeString(movie_poster);
        dest.writeFloat(movie_ave_vote);
        dest.writeString(movie_overview);
        dest.writeString(movie_backdrop);
        dest.writeString(movie_release_date);
        dest.writeString(movie_original_title);
    }

}

