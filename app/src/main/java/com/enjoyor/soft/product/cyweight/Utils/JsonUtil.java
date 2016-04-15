package com.enjoyor.soft.product.cyweight.Utils;

import com.alibaba.fastjson.JSON;
import com.enjoyor.soft.product.cyweight.Entity.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqiang on 2016/4/14 11:04.
 */
public class JsonUtil {
    public static List<Item> pareJson(String jsonStr){
        List<Item> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonStr);
            String array = object.getString("getDetailByRfidCodeResult");
            JSONArray array1 = new JSONArray(array);
            for (int i= 0;i<array1.length();i++){
                String item_carnum = array1.getJSONObject(i).getString("车牌");
                String item_category = array1.getJSONObject(i).getString("品种");
                String item_singlecount = array1.getJSONObject(i).getString("单次包数");
                String item_pizhong = array1.getJSONObject(i).getString("皮重");
                Item item = new Item(item_carnum,item_category,item_singlecount,item_pizhong);
                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
