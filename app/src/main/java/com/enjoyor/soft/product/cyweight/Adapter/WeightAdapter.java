package com.enjoyor.soft.product.cyweight.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.enjoyor.soft.R;
import com.enjoyor.soft.product.cyweight.Entity.Detail;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by huqiang on 2016/4/12 10:16.
 */
public class WeightAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Detail> list = new ArrayList<>();

    public WeightAdapter(Context mcontext, List<Detail> list) {
        this.mcontext = mcontext;
        this.list = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;
        if (convertView ==null){
            holder = new viewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.custom_dialog_item,parent,false);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_single = (TextView) convertView.findViewById(R.id.tv_single);
            holder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
            holder.btn_del = (Button) convertView.findViewById(R.id.btn_del);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        Detail detail = list.get(position);
        holder.tv_num.setText(detail.getId());
        holder.tv_single.setText(detail.getSingle_count());
        holder.tv_weight.setText(detail.getWeight());
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    class viewHolder{
        TextView tv_num;
        TextView tv_single;
        TextView tv_weight;
        Button btn_del;
    }
}
