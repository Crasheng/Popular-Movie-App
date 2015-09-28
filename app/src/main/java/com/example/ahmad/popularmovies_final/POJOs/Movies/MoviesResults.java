package com.example.ahmad.popularmovies_final.POJOs.Movies;

/**
 * Created by crasheng on 9/26/15.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MoviesResults {

    private Boolean adult;
    private String backdrop_path;
    private List<Integer> genreIds = new ArrayList<Integer>();
    private Integer id;
    private String original_language;
    private String original_title;
    private String overview;
    private String release_date;
    private String poster_path;
    private Double popularity;
    private String title;
    private Boolean video;
    private Double vote_average;
    private Integer vote_count;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public MoviesResults() {
    }

    /**
     *
     * @param id
     * @param genreIds
     * @param title
     * @param release_date
     * @param overview
     * @param poster_path
     * @param original_title
     * @param vote_average
     * @param original_language
     * @param adult
     * @param backdrop_path
     * @param vote_count
     * @param video
     * @param popularity
     */
    public MoviesResults(Boolean adult, String backdrop_path, List<Integer> genreIds, Integer id, String original_language, String original_title, String overview, String release_date, String poster_path, Double popularity, String title, Boolean video, Double vote_average, Integer vote_count) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genreIds = genreIds;
        this.id = id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    /**
     *
     * @return
     * The adult
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     *
     * @param adult
     * The adult
     */
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    /**
     *
     * @return
     * The backdrop_path
     */
    public String getBackdrop_path() {
        return backdrop_path;
    }

    /**
     *
     * @param backdrop_path
     * The backdrop_path
     */
    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    /**
     *
     * @return
     * The genreIds
     */
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    /**
     *
     * @param genreIds
     * The genre_ids
     */
    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The original_language
     */
    public String getOriginal_language() {
        return original_language;
    }

    /**
     *
     * @param original_language
     * The original_language
     */
    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    /**
     *
     * @return
     * The original_title
     */
    public String getOriginalTitle() {
        return original_title;
    }

    /**
     *
     * @param originalTitle
     * The original_title
     */
    public void setOriginalTitle(String originalTitle) {
        this.original_title = originalTitle;
    }

    /**
     *
     * @return
     * The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     *
     * @param overview
     * The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     *
     * @return
     * The release_date
     */
    public String getRelease_date() {
        return release_date;
    }

    /**
     *
     * @param release_date
     * The release_date
     */
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    /**
     *
     * @return
     * The poster_path
     */
    public String getPoster_path() {
        return poster_path;
    }

    /**
     *
     * @param poster_path
     * The poster_path
     */
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    /**
     *
     * @return
     * The popularity
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     *
     * @param popularity
     * The popularity
     */
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The video
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     *
     * @param video
     * The video
     */
    public void setVideo(Boolean video) {
        this.video = video;
    }

    /**
     *
     * @return
     * The vote_average
     */
    public Double getVote_average() {
        return vote_average;
    }

    /**
     *
     * @param vote_average
     * The vote_average
     */
    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    /**
     *
     * @return
     * The vote_count
     */
    public Integer getVote_count() {
        return vote_count;
    }

    /**
     *
     * @param vote_count
     * The vote_count
     */
    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}