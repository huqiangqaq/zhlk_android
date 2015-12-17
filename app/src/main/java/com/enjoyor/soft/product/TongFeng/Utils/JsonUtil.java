package com.enjoyor.soft.product.TongFeng.Utils;

import com.alibaba.fastjson.JSON;
import com.enjoyor.soft.product.TongFeng.Entity.TongFeng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 83916 on 2015/12/4.
 */
public class JsonUtil {

    public static List<TongFeng> parseJson(String json) {   //定义flag，根据仓库编号获取相应的json数据
        try {
            JSONObject object = new JSONObject();
            JSONArray array = object.getJSONArray("data");
            return JSON.parseArray(array.toString(), TongFeng.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object[] parseCangkuJson(String json){
        try {
            JSONObject object = new JSONObject();
            JSONArray array  = object.getJSONArray("data");
           // JSONArray array = new JSONArray(json);
            return JSON.parseArray(array.toString()).toArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
