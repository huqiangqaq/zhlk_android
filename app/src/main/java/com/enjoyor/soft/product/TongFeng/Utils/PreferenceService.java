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

    public PreferenceService(Context context) {
        this.context = context;
    }

    public void save(String duankouip){
        SharedPreferences preferences = context.getSharedPreferences("IP",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("duankou",duankouip);
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
        SharedPreferences preference = context.getSharedPreferences("IP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("ip1", ip1);
        editor.putString("ip2",ip2);
        editor.putString("ip3",ip3);
        editor.putString("ip4",ip4);
        editor.commit();//把数据提交会文件
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
        return maps;
    }
}
