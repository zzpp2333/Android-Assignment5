package com.bytedance.android.lesson.restapi.solution.bean;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Xavier.S
 * @date 2019.01.17 18:08
 */
public class Cat {

    // TODO-C1 (1) Implement your Cat Bean here according to the response json
    /**
     * type : success
     * value : {"breeds":[],"id":"a4j","url":"https://cdn2.thecatapi.com/images/a4j.jpg","width":900,"height":593}
     */

    @SerializedName("breeds") private JsonArray breeds;
    @SerializedName("id") private String id;
    @SerializedName("url") private String url;
    @SerializedName("width") private int width;
    @SerializedName("height") private int height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JsonArray getBreeds() {
        return breeds;
    }

    public void setBreeds(JsonArray breeds) {
        this.breeds = breeds;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override public String toString() {
        return "Value{" +
                "breeds" + breeds +
                "id=" + id +
                ", url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
