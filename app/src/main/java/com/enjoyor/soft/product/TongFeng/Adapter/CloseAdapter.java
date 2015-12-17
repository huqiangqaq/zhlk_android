package com.enjoyor.soft.product.TongFeng.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.enjoyor.soft.R;
import com.enjoyor.soft.product.TongFeng.Entity.TongFeng;

import java.util.List;

/**
 * Created by 83916 on 2015/12/9.
 */
public class CloseAdapter extends BaseAdapter{
    private List<TongFeng> list;
    private Context context;
    private ViewHolder viewHolder;
    private TongFeng tongFeng;

    public CloseAdapter(Context context, List<TongFeng> list) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            viewHolder.ib_swift = (ImageButton) convertView.findViewById(R.id.ib_swift);
            viewHolder.etName = (EditText) convertView.findViewById(R.id.etName);
            viewHolder.tv_Status = (TextView) convertView.findViewById(R.id.tv_Status);
            viewHolder.ib_distance = (ImageButton) convertView.findViewById(R.id.ib_distance);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        tongFeng = list.get(position);
        viewHolder.etName.setText(tongFeng.getTfBarnDevicesNo().toString());   //设备编号
        viewHolder.tv_Status.setText(tongFeng.getStatus1().toString());
        viewHolder.ib_swift.setBackgroundResource(R.drawable.icon_05);
        if ("关闭".equals(tongFeng.getReMoteControl())) {
            viewHolder.ib_distance.setBackgroundResource(R.drawable.icon_05);
        } else {
            viewHolder.ib_distance.setBackgroundResource(R.drawable.icon_06);
        }
        return convertView;
    }
    class ViewHolder{
        public ImageButton ib_swift;
        public EditText etName;
        public TextView tv_Status;
        public ImageButton ib_distance;
    }
}
