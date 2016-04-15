package com.enjoyor.soft.product.cyweight.Entity;

/**
 * Created by huqiang on 2016/4/14 11:12.
 */
public class Item {
    private String item_carnum;
    private String item_category;
    private String item_singlecount;
    private String item_pizhong;

    public Item() {
    }

    public Item(String item_carnum, String item_category, String item_singlecount, String item_pizhong) {
        this.item_carnum = item_carnum;
        this.item_category = item_category;
        this.item_singlecount = item_singlecount;
        this.item_pizhong = item_pizhong;
    }

    public String getItem_carnum() {
        return item_carnum;
    }

    public void setItem_carnum(String item_carnum) {
        this.item_carnum = item_carnum;
    }

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    public String getItem_singlecount() {
        return item_singlecount;
    }

    public void setItem_singlecount(String item_singlecount) {
        this.item_singlecount = item_singlecount;
    }

    public String getItem_pizhong() {
        return item_pizhong;
    }

    public void setItem_pizhong(String item_pizhong) {
        this.item_pizhong = item_pizhong;
    }

    @Override
    public String toString() {
        return "Item{" +
                "item_carnum='" + item_carnum + '\'' +
                ", item_category='" + item_category + '\'' +
                ", item_singlecount='" + item_singlecount + '\'' +
                ", item_pizhong='" + item_pizhong + '\'' +
                '}';
    }
}
