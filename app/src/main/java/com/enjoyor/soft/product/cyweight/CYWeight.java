package com.enjoyor.soft.product.cyweight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.enjoyor.soft.R;
import com.enjoyor.soft.product.cyweight.Adapter.WeightAdapter;
import com.enjoyor.soft.product.cyweight.Entity.Detail;
import com.enjoyor.soft.product.cyweight.Service.LongRunningService;

import java.util.ArrayList;
import java.util.List;

public class CYWeight extends Activity implements View.OnClickListener {
    private TextView tv_num,tv_category,tv_count,tv_averageWieght,tv_pizhong,tv_averageAure,detail_num,detail_maozhong,detail_chupi,detail_jingzhong,tv_curr_Weight;
    private EditText et_singlecount;
    private Button btn_single,btn_end,btn_detail;
    private ListView lv_detail;
    private WeightAdapter adapter;
    private List<Detail> list = new ArrayList<>();
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyweight);
        init();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.broadcast.RECEIVER_ACTION");
        registerReceiver(myReceiver, filter);
        intent = new Intent(this,LongRunningService.class);
        startService(intent);


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
                int i = 1;
                detail_num.setText((Integer.parseInt(et_singlecount.getText().toString()))*i);

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
}
