package com.enjoyor.soft.product.TongFeng.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 83916 on 2015/12/9.
 */
public class PreferenceService {
    private Context context;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public PreferenceService(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("IP",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void save(String duankouip){

        editor.putString("duankou",duankouip);
        editor.commit();
    }

    public void save(String UserName,String PassWord){
        editor.putString("UserName",UserName);
        editor.putString("PassWord",PassWord);
        editor.commit();
    }

    /**
     *
     * 保存参数
     * @param ip1
     * @param ip2
     * @param ip3
     * @param ip4
     * @throws Exception
     */

    public void save(String ip1,String ip2,String ip3,String ip4) throws Exception{
//        preferences = context.getSharedPreferences("IP", Context.MODE_PRIVATE);
//        editor = preferences.edit();
        editor.putString("ip1", ip1);
        editor.putString("ip2",ip2);
        editor.putString("ip3",ip3);
        editor.putString("ip4",ip4);
        editor.commit();//把数据提交会文件
    }

    public void save(String loginip1,String loginip2,String loginip3,String loginip4,String flag){
        if ("Login".equals(flag)){
            editor.putString("loginip1",loginip1);
            editor.putString("loginip2",loginip2);
            editor.putString("loginip3",loginip3);
            editor.putString("loginip4",loginip4);
            editor.commit();//把数据提交会文件
        }
    }

    /**
     * 获取各项配置参数
     * @return
     */
    public Map<String, String> getPreferences(){
        Map<String, String> maps = new HashMap<String, String>();
        SharedPreferences preference = context.getSharedPreferences("IP", Context.MODE_PRIVATE);
        maps.put("ip1", preference.getString("ip1", ""));
        maps.put("ip2", preference.getString("ip2", ""));
        maps.put("ip3", preference.getString("ip3", ""));
        maps.put("ip4", preference.getString("ip4", ""));
        maps.put("duankouip",preference.getString("duankou",""));
        maps.put("loginip1",preference.getString("loginip1",""));
        maps.put("loginip2",preference.getString("loginip2",""));
        maps.put("loginip3",preference.getString("loginip3",""));
        maps.put("loginip4",preference.getString("loginip4",""));
        maps.put("UserName",preference.getString("UserName",""));
        maps.put("PassWord",preference.getString("PassWord",""));
        return maps;
    }
}
