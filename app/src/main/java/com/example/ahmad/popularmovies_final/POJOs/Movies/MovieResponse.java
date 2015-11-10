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
public class MovieResponse {

    private Integer page;
    private List<MoviesResults> results = new ArrayList<MoviesResults>();
    private Integer total_pages;
    private Integer total_results;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public MovieResponse() {
    }

    /**
     *
     * @param results
     * @param total_results
     * @param page
     * @param total_pages
     */
    public MovieResponse(Integer page, List<MoviesResults> results, Integer total_pages, Integer total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    /**
     *
     * @return
     * The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The results
     */
    public List<MoviesResults> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<MoviesResults> results) {
        this.results = results;
    }

    /**
     *
     * @return
     * The total_pages
     */
    public Integer getTotal_pages() {
        return total_pages;
    }

    /**
     *
     * @param total_pages
     * The total_pages
     */
    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    /**
     *
     * @return
     * The total_results
     */
    public Integer getTotal_results() {
        return total_results;
    }

    /**
     *
     * @param total_results
     * The total_results
     */
    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}