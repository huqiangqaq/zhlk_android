package com.enjoyor.soft.product.TongFeng.Utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.enjoyor.soft.product.TongFeng.Entity.TongFeng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 83916 on 2015/12/4.
 */
public class JsonUtil {

    public static List<TongFeng> parseJson(String json) {   //定义flag，根据仓库编号获取相应的json数据
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("data");
            return JSON.parseArray(array.toString(), TongFeng.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] parseCangkuJson(String json){
//        List<String> cangkuList = new ArrayList<>();
//        String jsonStr =null;
//        try {
//            JSONObject object = new JSONObject(json);
//            JSONArray array  = object.getJSONArray("data");
//            for (int i=0;i<array.length();i++){
//                jsonStr =array.getJSONObject(i).getString("BarNo");
//
//            }
//            cangkuList.add(jsonStr);
//
//           // JSONArray array = new JSONArray(json);
//            return cangkuList;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        String[] strs=null;
        //List<String> cangkulist = new ArrayList<>();
        try {
            JSONObject jsonStr=new JSONObject(json);
            JSONArray arr=jsonStr.getJSONArray("data");
            if(arr!=null&&arr.length()>0){
                strs=new String[arr.length()];
                System.out.println(arr.length());
                for(int i=0;i<arr.length();i++){
                    strs[i]=arr.getJSONObject(i).getString("BarnNo");
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return strs;
    }
}
