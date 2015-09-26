package com.example.ahmad.popularmovies_final.POJOs.Videos;

/**
 * Created by crasheng on 9/26/15.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class VideosResponse {

    private Integer id;
    private List<VideosResult> results = new ArrayList<VideosResult>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public VideosResponse() {
    }

    /**
     *
     * @param id
     * @param results
     */
    public VideosResponse(Integer id, List<VideosResult> results) {
        this.id = id;
        this.results = results;
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
     * The results
     */
    public List<VideosResult> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<VideosResult> results) {
        this.results = results;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}