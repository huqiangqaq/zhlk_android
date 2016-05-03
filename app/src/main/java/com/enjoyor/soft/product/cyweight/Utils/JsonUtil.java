package com.enjoyor.soft.product.cyweight.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    public static List<Item> pareJson(Context context,String jsonStr){
        List<Item> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonStr);
            String array = object.getString("getDetailByRfidCodeResult");
            JSONArray array1 = new JSONArray(array);
            if (array1.length()>0){
                for (int i= 0;i<array1.length();i++){
                    String item_carnum = array1.getJSONObject(i).getString("车牌");
                    String item_category = array1.getJSONObject(i).getString("品种");
                    String item_singlecount = array1.getJSONObject(i).getString("单次包数");
                    String item_pizhong = array1.getJSONObject(i).getString("皮重");
                    Item item = new Item(item_carnum,item_category,item_singlecount,item_pizhong);
                    list.add(item);
            }
            }else {
                Toast.makeText(context,"当前卡号无效，请更换IC卡",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String pareWeight(String jsonStr){
        String weight = "";
        try {
            JSONObject object = new JSONObject(jsonStr);
            String array = object.getString("getWeightResult");
            JSONArray array1 = new JSONArray(array);
            for (int i=0;i<array1.length();i++){
                weight = array1.getJSONObject(i).getString("重量");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weight;
    }

    public static String parseResult_return(String jsonStr){
        String result = "";
        try {
            JSONObject object = new JSONObject(jsonStr);
            String data = object.getString("PostSingleWeightRecordResult");
            JSONArray array = new JSONArray(data);
            for (int i=0;i<array.length();i++){
                result = array.getJSONObject(i).getString("处理结果");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String parseResut_end(String jsonStr){
        String result = "";
        try {
            JSONObject object = new JSONObject(jsonStr);
            String data = object.getString("PostALLWeightRecordResult");
            JSONArray array = new JSONArray(data);
            for (int i = 0;i<array.length();i++){
                result = array.getJSONObject(i).getString("处理结果");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
