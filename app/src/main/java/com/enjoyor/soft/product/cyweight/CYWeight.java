package com.enjoyor.soft.product.cyweight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.product.cyweight.Adapter.WeightAdapter;
import com.enjoyor.soft.product.cyweight.Entity.Detail;
import com.enjoyor.soft.product.cyweight.Entity.Item;
import com.enjoyor.soft.product.cyweight.Service.LongRunningService;
import com.enjoyor.soft.product.cyweight.Utils.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import cilico.tools.Nfcreceive;
import okhttp3.Call;

public class CYWeight extends Activity implements View.OnClickListener {
    private TextView tv_num,tv_category,tv_count,tv_averageWieght,tv_pizhong,tv_averageAure,detail_num,detail_maozhong,detail_chupi,detail_jingzhong,tv_curr_Weight;
    private EditText et_singlecount;
    private Button btn_single,btn_end,btn_detail;
    private ListView lv_detail;
    private WeightAdapter adapter;
    private List<Detail> list = new ArrayList<>();
    private List<Item> item_list = new ArrayList<>();
    private Intent intent;
    private int i = 1;
    String rfidGuid=null;//寻卡成功标志
    private String rfidCode = null;   //接受的卡号
    private Handler mnfcHandler = new MainNfcHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyweight);
        init();
        rfidCode=Nfcreceive.readSigOneBlock(Constants.PASSWORD, Constants.ADD);    //获取卡号
        openReceiver();
        OpenService();
        if (!(null==rfidGuid)){
            getNetConn();
        }else {
            Toast.makeText(CYWeight.this,"请先扫描IC卡",Toast.LENGTH_SHORT).show();
        }

    }

    private void OpenService() {
        intent = new Intent(this,LongRunningService.class);
        startService(intent);
    }

    private void openReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.broadcast.RECEIVER_ACTION");
        registerReceiver(myReceiver, filter);
    }
    private void getNetConn(){
        OkHttpUtils.get().url(Constants.ITEM_URL+"11").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                item_list = JsonUtil.pareJson(response);
                Log.d("cyweight",item_list.size()+"");
                tv_num.setText(item_list.get(0).getItem_carnum());
                tv_category.setText(item_list.get(0).getItem_category());
                et_singlecount.setText(item_list.get(0).getItem_singlecount());
                tv_pizhong.setText(item_list.get(0).getItem_pizhong());
            }
        });
    }

    private void init() {
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_category = (TextView) findViewById(R.id.tv_category);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_averageWieght = (TextView) findViewById(R.id.tv_averageWieght);
        tv_pizhong = (TextView) findViewById(R.id.tv_pizhong);
        tv_averageAure = (TextView) findViewById(R.id.tv_averageAure);
        detail_num = (TextView) findViewById(R.id.detail_num);
        detail_maozhong = (TextView) findViewById(R.id.detail_maozhong);
        detail_chupi = (TextView) findViewById(R.id.detail_chupi);
        detail_jingzhong = (TextView) findViewById(R.id.detail_jingzhong);
        et_singlecount = (EditText) findViewById(R.id.et_singlecount);
        btn_single = (Button) findViewById(R.id.btn_single);
        btn_end = (Button) findViewById(R.id.btn_end);
        btn_detail = (Button) findViewById(R.id.btn_detail);
        btn_single.setOnClickListener(this);
        btn_end.setOnClickListener(this);
        btn_detail.setOnClickListener(this);
        //后台轮询查询当前重量
        tv_curr_Weight = (TextView) findViewById(R.id.tv_curr_Weight);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_detail:
                AlertDialog.Builder builder = new AlertDialog.Builder(CYWeight.this);
                builder.setIcon(R.drawable.ic_launcher);
                //通过layoutinflater来加载一个布局
                View view = LayoutInflater.from(CYWeight.this).inflate(R.layout.custom_dialog,null);
                builder.setView(view);
                lv_detail = (ListView) view.findViewById(R.id.lv_Content);
                adapter = new WeightAdapter(CYWeight.this,list);
                lv_detail.setAdapter(adapter);
                builder.show();
                break;
            case R.id.btn_single:
                //每次的操作记录存放到list中
                Detail detail = new Detail(i+"",et_singlecount.getText().toString(),detail_maozhong.getText().toString());
                list.add(detail);
                int count =(Integer.parseInt(tv_count.getText().toString()))%(Integer.parseInt(et_singlecount.getText().toString()));
                if (i<=count){
                    detail_num.setText((Integer.parseInt(et_singlecount.getText().toString()))*i);
                    i++;
                }else {
                    btn_single.setEnabled(false);
                    i = 1;
                    Toast.makeText(CYWeight.this,"已达最大包数，请结束称重",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_end:
                break;


        }
    }
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, LongRunningService.class);
            String data = intent.getExtras().getString("data");
            Log.d("huqiang", data);
            tv_curr_Weight.setText(data+"KG");
            context.startService(i);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CYWeight","on destroy");
        unregisterReceiver(myReceiver);
        stopService(intent);
    }

    /**
     * @author Administrator
     * 接收NFC消息：
     * dongqiwu在
     * */
    private class MainNfcHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==2) {
                rfidGuid=(String)msg.obj;
                //txt_code.setText((String)msg.obj);
                //m_nCount++;
                //txt_scancount.setText("Scanning:"+String.valueOf(m_nCount));
                //将结果保存到数据库：
                //清空状态：
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //txt_code.setText("");
                    }
                }, 1000);
            }

            super.handleMessage(msg);
        }
    };
}
