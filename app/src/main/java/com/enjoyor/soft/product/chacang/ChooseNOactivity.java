package com.enjoyor.soft.product.chacang;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.common.HtlClientCallBack;
import com.enjoyor.soft.common.HtlClientUtils;
import com.google.gson.Gson;

/**
 *========================================
 * 说明：选择仓号
 * 作者：胡团乐
 * 项目：zhlk_android
 * 时间：2014年10月14日 下午2:11:36
 *========================================
 */
public class ChooseNOactivity extends Activity{

	ListView choose_list;
	List<ChooseNOBean> cnbs;
	ChooseAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_no);
		choose_list = (ListView) findViewById(R.id.choose_list);
		cnbs = new ArrayList<ChooseNOBean>();
		adapter = new ChooseAdapter();
		adapter.setCnbs(cnbs);
		choose_list.setAdapter(adapter);
		refreshData();
		choose_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("storeno", cnbs.get(position).getStoreNo());
				intent.putExtra("storename", cnbs.get(position).getStoreName());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
	ProgressDialog pd;
	public void refreshData(){
		pd = new ProgressDialog(this);pd.setMessage("正在查询...");
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		String url = Constants.WEBURIHEAD+"storeHouseAjax!queryStoreHouse.htm";
		HtlClientUtils.getContent(url, null, true, new HtlClientCallBack() {
			
			@Override
			public void contentResponse(boolean isSuccess, int statusCode, Header[] headers, byte[] responseBody) {
				if(pd!=null) pd.dismiss();
				if(isSuccess){
					try {
						//String json = "{    'returnMsgMap': {        'returnAjaxState': '3'    },    'storeHouseMap': {        'CK001': '一号 仓',        'CK002': '二号仓',        'CK003': '三号仓',        'CK004': '四号仓',        'CK005': '五号仓',        'CK006': ' 六号仓',        'CK007': '七号仓',        'CK008': '八号仓',        'CK009': '九号仓',        'CK010': '十号 仓',        'CK011': '十一号仓',        'CK012': '十二号仓',        'CK013': '十三号仓',        'CK014': '十四号 仓',        'CK015': '十五号仓',        'CK016': '十六号仓',        'CK017': '十七号仓',        'CK018': '十八号 仓',        'CK019': '十九号仓',        'CK020': '二十号仓',        'CK021': '二十一号仓',        'CK022': '二十二号仓 ',        'CK023': '二十三号仓',        'CK024': '二十四号仓',        'CK025': '二十五号仓',        'CK026': '二十六号 仓',        'CK027': '二十七号仓',        'CK028': '二十八号仓',        'CK029': '二十九号仓',        'CK030': '三十号 仓',        'CK031': '三十一号仓',        'CK032': '三十二号仓',        'CK033': '三十三号仓',        'CK034': '三十四 号仓'    }}";
						String json = new String(responseBody);
						ChooseReturn cr = new Gson().fromJson(json, ChooseReturn.class);
						Log.i("five", cr.toString());
						for(String s:cr.getStoreHouseMap().keySet()){
							cnbs.add(new ChooseNOBean(s, cr.getStoreHouseMap().get(s)));
						}
						adapter.setCnbs(cnbs);
					} catch (Exception e) {
						Toast.makeText(ChooseNOactivity.this, "解析异常", 1).show();
					}
					
				}else{
					Toast.makeText(ChooseNOactivity.this, "网络异常", 1).show();
				}
			}
		});
		
		/*for(ChooseNOBean c:cnbs){
			Log.i("five", c.getStoreNo()+" "+c.getStoreName());
		}*/
	}
	class ChooseAdapter extends BaseAdapter{
		List<ChooseNOBean> cnbs;
		
		public void setCnbs(List<ChooseNOBean> cnbs) {
			this.cnbs = cnbs;
			this.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cnbs.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return cnbs.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = LayoutInflater.from(ChooseNOactivity.this).inflate(R.layout.chacang_choose_item, null);
				holder.no = (TextView) convertView.findViewById(R.id.no);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.no.setText(cnbs.get(position).getStoreNo());
			holder.name.setText(cnbs.get(position).getStoreName());
			return convertView;
		}
		
		class ViewHolder{
			TextView no;
			TextView name;
		}
	}
	
}
