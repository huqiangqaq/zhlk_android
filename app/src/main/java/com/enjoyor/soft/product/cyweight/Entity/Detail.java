package com.enjoyor.soft.product.cyweight.Entity;

/**
 * Created by huqiang on 2016/4/12 10:23.
 */
public class Detail {
    private String id;
    private String single_count;
    private String weight;

    public Detail() {
    }

    public Detail(String id, String single_count, String weight) {
        this.id = id;
        this.single_count = single_count;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSingle_count() {
        return single_count;
    }

    public void setSingle_count(String single_count) {
        this.single_count = single_count;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id='" + id + '\'' +
                ", single_count='" + single_count + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
