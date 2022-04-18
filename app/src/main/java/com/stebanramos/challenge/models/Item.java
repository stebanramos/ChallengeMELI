package com.stebanramos.challenge.models;

import org.json.JSONArray;

public class Item {

    private String id;
    private String title;
    private JSONArray píctures;
    private String price;
    private JSONArray attributes;

    public Item(String id, String title, JSONArray píctures, String price, JSONArray attributes) {
        this.id = id;
        this.title = title;
        this.píctures = píctures;
        this.price = price;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JSONArray getPíctures() {
        return píctures;
    }

    public void setPíctures(JSONArray píctures) {
        this.píctures = píctures;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public JSONArray getAttributes() {
        return attributes;
    }

    public void setAttributes(JSONArray attributes) {
        this.attributes = attributes;
    }
}
