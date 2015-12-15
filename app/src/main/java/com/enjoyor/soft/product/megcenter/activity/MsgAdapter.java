package com.enjoyor.soft.product.megcenter.activity;

import java.util.List;

import com.enjoyor.soft.R;
import com.enjoyor.soft.product.megcenter.model.Msg;
import com.enjoyor.soft.product.megcenter.model.MsgBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MsgAdapter extends BaseAdapter{

	List<Msg> msgs;
	Context context;
	
	public MsgAdapter(List<Msg> msgs, Context context) {
		super();
		this.msgs = msgs;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return msgs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return msgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(context).inflate(R.layout.msg_list_item, null);
		TextView item_sponsor = (TextView) v.findViewById(R.id.item_sponsor);
		TextView item_title = (TextView) v.findViewById(R.id.item_title);
		TextView item_date = (TextView) v.findViewById(R.id.item_date);
		TextView right_blue = (TextView) v.findViewById(R.id.right_blue);
		
		Msg m = msgs.get(position);
		MsgBean mb = m.getMsgBean();
		item_sponsor.setText(mb.getSource());
//		item_sponsor.setText("系统提示:");
		item_title.setText(mb.getInformationName());
		item_date.setText("时间:"+mb.getSendTime());
		if("0".equals(m.getCheckState())){
			right_blue.setVisibility(View.VISIBLE);
		}else{
			right_blue.setVisibility(View.GONE);
		}
		return v;
	}

}
