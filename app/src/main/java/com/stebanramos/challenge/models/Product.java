package com.stebanramos.challenge.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Product{

    private String id;
    private String title;
    private String price;
    private String available_quantity;
    private String condition;
    private String permalink;
    private String thumbnail;
    private String attributes;
    private String category_id;
    private JSONObject installments;
    private boolean free_shipping;

    public Product(String id, String title, String price, String available_quantity, String condition, String permalink, String thumbnail, String attributes, String category_id, JSONObject installments, boolean free_shipping) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.available_quantity = available_quantity;
        this.condition = condition;
        this.permalink = permalink;
        this.thumbnail = thumbnail;
        this.attributes = attributes;
        this.category_id = category_id;
        this.installments = installments;
        this.free_shipping = free_shipping;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(String available_quantity) {
        this.available_quantity = available_quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public JSONObject getInstallments() {
        return installments;
    }

    public void setInstallments(JSONObject installments) {
        this.installments = installments;
    }

    public boolean isFree_shipping() {
        return free_shipping;
    }

    public void setFree_shipping(boolean free_shipping) {
        this.free_shipping = free_shipping;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", available_quantity='" + available_quantity + '\'' +
                ", condition='" + condition + '\'' +
                ", permalink='" + permalink + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", attributes='" + attributes + '\'' +
                ", category_id='" + category_id + '\'' +
                ", installments=" + installments +
                ", free_shipping=" + free_shipping +
                '}';
    }
}
