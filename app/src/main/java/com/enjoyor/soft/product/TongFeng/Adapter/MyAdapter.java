package com.enjoyor.soft.product.TongFeng.Adapter;

import android.app.Instrumentation;
import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputConnection;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.product.TongFeng.Entity.TongFeng;
import com.enjoyor.soft.product.TongFeng.TongFengActivity;
import com.enjoyor.soft.product.TongFeng.Utils.HttpUtil;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.security.Policy;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 83916 on 2015/12/4.
 */
public class MyAdapter extends BaseAdapter {

    private List<TongFeng> tongFengList;
    private Context context;
    private boolean status = false;   //开关的状态，默认为关
    private TongFeng tongFeng;
    private ViewHolder viewHolder;
    private String tongfengIP;


    //private int position;               //当前点击item的position
    public MyAdapter(Context context, List<TongFeng> tongFengList,String tongfengIP) {
        this.tongFengList = tongFengList;
        this.context = context;
        this.tongfengIP = tongfengIP;

    }
    @Override
    public int getCount() {
        return tongFengList.size();
    }

    @Override
    public Object getItem(int position) {
        return tongFengList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            viewHolder.etName = (EditText) convertView.findViewById(R.id.etName);
            viewHolder.ib_swift = (ImageButton) convertView.findViewById(R.id.ib_swift);
            viewHolder.ib_distance = (ImageButton) convertView.findViewById(R.id.ib_distance);
            viewHolder.tv_Status = (TextView) convertView.findViewById(R.id.tv_Status);
            viewHolder.tv_Msg = (TextView) convertView.findViewById(R.id.tv_Msg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        tongFeng = tongFengList.get(position);
        Log.i("123++++++++++", tongFeng.getTfBarnDevicesNo().toString());
        viewHolder.etName.setText(tongFeng.getChineseName().toString());   //设备编号
        viewHolder.tv_Status.setText(tongFeng.getStatus1().toString());
        viewHolder.tv_Msg.setText(tongFeng.getMsg().toString());
        /**
         * 组件初始化
         */
        if ("开到位".equals(tongFeng.getStatus1())) {  //开状态
            viewHolder.ib_swift.setBackgroundResource(R.drawable.icon_06);
            status = true;
        } else if ("关到位".equals(tongFeng.getStatus1()) || "异常".equals(tongFeng.getStatus1())) {//关状态
            viewHolder.ib_distance.setBackgroundResource(R.drawable.icon_05);
            status = false;
        }
        if ("关闭".equals(tongFeng.getReMoteControl())) {
            viewHolder.ib_distance.setBackgroundResource(R.drawable.icon_05);
        } else {
            viewHolder.ib_distance.setBackgroundResource(R.drawable.icon_06);
        }
        viewHolder.ib_swift.setTag(position);
        //给BUTTON设置点击事件，添加点击事件后listview将失去焦点 ，需要把button的焦点去掉
        final ViewHolder finalViewHolder = viewHolder;
        finalViewHolder.ib_swift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status) {
                    int position = (int) v.getTag();
                    //上传参数给服务器，需加上仓库编号
//                        JSONStringer vehicle = new JSONStringer().object().key("BarnNo").value(tongFengList.get(position).getBarnNo()).key("tfBarnDevicesNo").value(tongFengList.get(position).getBarnName())
//
//                                .key("Status1").value("0")
//                                .key("ReMoteControl").value(tongFengList.get(position).getReMoteControl()).endObject();
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("BarnNo", tongFengList.get(position).getBarnNo());
                    params.addBodyParameter("tfBarnDevicesNo", tongFengList.get(position).getTfBarnDevicesNo());
                    params.addBodyParameter("Status1", "0");
                    params.addBodyParameter("ReMoteControl", tongFengList.get(position).getReMoteControl());
                    HttpUtil.GetJsonFromNet(context, tongfengIP+"OpenClose/" + tongFengList.get(position).getBarnNo()
                                + "/" + tongFengList.get(position).getTfBarnDevicesNo() + "/" + "0" + "/" + tongFengList.get(position).getReMoteControl(), params);

                    finalViewHolder.ib_swift.setBackgroundResource(R.drawable.icon_05);
                    status = false;
                } else {
                    int position = (int) v.getTag();
//                        JSONStringer vehicle = new JSONStringer().object().key("BarnNo").value(tongFengList.get(position).getBarnNo()).key("name").value(tongFengList.get(position).getBarnName())
//
//                                .key("开关到位").value("1")
//                                .key("远程到位").value(tongFengList.get(position).getReMoteControl()).endObject();
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("BarnNo", tongFengList.get(position).getBarnNo());
                    params.addBodyParameter("tfBarnDevicesNo", tongFengList.get(position).getTfBarnDevicesNo());
                    params.addBodyParameter("Status1", "0");
                    params.addBodyParameter("ReMoteControl", tongFengList.get(position).getReMoteControl());
                    HttpUtil.GetJsonFromNet(context, tongfengIP+"OpenClose/"+ tongFengList.get(position).getBarnNo()
                               + "/" + tongFengList.get(position).getTfBarnDevicesNo() + "/" + "0" + "/" + tongFengList.get(position).getReMoteControl(), params);
                    finalViewHolder.ib_swift.setBackgroundResource(R.drawable.icon_06);
                    status = true;
                    //"http://192.168.1.177:7000/OpenClose/"
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        public EditText etName;
        public ImageButton ib_swift;
        public ImageButton ib_distance;
        public TextView tv_Status;
        public TextView tv_Msg;
    }
}
