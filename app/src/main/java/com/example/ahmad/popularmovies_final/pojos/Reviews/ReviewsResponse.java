package com.example.ahmad.popularmovies_final.pojos.Reviews;

/**
 * Created by crasheng on 9/26/15.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ReviewsResponse {

    private Integer id;
    private Integer page;
    private List<ReviewsResults> results = new ArrayList<ReviewsResults>();
    private Integer total_pages;
    private Integer total_results;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public ReviewsResponse() {
    }

    /**
     *
     * @param id
     * @param results
     * @param total_results
     * @param page
     * @param total_pages
     */
    public ReviewsResponse(Integer id, Integer page, List<ReviewsResults> results, Integer total_pages, Integer total_results) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
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
    public List<ReviewsResults> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<ReviewsResults> results) {
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