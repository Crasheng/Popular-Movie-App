package com.example.ahmad.popularmovies_final.pojos.Videos;

/**
 * Created by crasheng on 9/26/15.
 */
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class VideosResult {

    private String id;
    private String iso6391;
    private String key;
    private String name;
    private String site;
    private Integer size;
    private String type;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public VideosResult() {
    }

    /**
     *
     * @param site
     * @param iso6391
     * @param id
     * @param name
     * @param type
     * @param key
     * @param size
     */
    public VideosResult(String id, String iso6391, String key, String name, String site, Integer size, String type) {
        this.id = id;
        this.iso6391 = iso6391;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The iso6391
     */
    public String getIso6391() {
        return iso6391;
    }

    /**
     *
     * @param iso6391
     * The iso_639_1
     */
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    /**
     *
     * @return
     * The key
     */
    public String getKey() {
        return key;
    }

    /**
     *
     * @param key
     * The key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The site
     */
    public String getSite() {
        return site;
    }

    /**
     *
     * @param site
     * The site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     *
     * @return
     * The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}