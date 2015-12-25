package com.enjoyor.soft.product.TongFeng.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.util.EntityUtils;
import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

/**
 * Created by 83916 on 2015/12/4.
 */
public class HttpUtil {
    private static com.lidroid.xutils.HttpUtils httpUtils;

    private static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils(5000);
        }
        return httpUtils;
    }

    //回调接口
    public interface GetJsonCallBack {
        public void callback(String jsonStr);
    }

    /**
     * get请求获取json数据
     *
     * @param context
     * @param url
     * @param jsonCallBack
     */
    public static void getJsonFromNet(final Context context, String url, final GetJsonCallBack jsonCallBack) {
        httpUtils = getHttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                jsonCallBack.callback(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * post请求获取json数据,上传参数个服务器
     *
     * @param context
     * @param url
     * @param params
     */
    public static void GetJsonFromNet(final Context context, String url, final RequestParams params, final GetJsonCallBack jsonCallBack ) {
        httpUtils = getHttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                jsonCallBack.callback(responseInfo.result);
                Log.i("NET",responseInfo.result);
                
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public static void getJsonFromNet(final Context context,String url,JSONStringer vehicle){
//        String strResp = "";  //接受返回结果
//        HttpPost post = new HttpPost(url);
//        //构造json
//           StringEntity entity = new StringEntity(vehicle.toString(),"UTF-8");
//            try {
//         post.setEntity(entity);
//
//            //向wcf服务发送请求
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpResponse response = httpClient.execute(post);
//
//            //判断是否成功
//            if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
//                strResp = EntityUtils.toString(response.getEntity(),"UTF-8");
//                Log.i("123++++++",strResp);
//                System.out.println(strResp);
//                Log.i("WebInvove","Saving: "+response.getStatusLine().getStatusCode());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
