package com.enjoyor.soft.product.xungeng;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.common.HtlClientCallBack;
import com.enjoyor.soft.common.HtlClientUtils;
import com.enjoyor.soft.product.chacang.ChacangActivity;
import com.enjoyor.soft.product.chacang.ChooseNOactivity;
import com.enjoyor.soft.product.megcenter.model.MsgJsonReturn;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
/*vo.setHumidity("84.1");//气湿
vo.setInDate(new Date());//时间
vo.setInsect("小米虫");//虫种
vo.setInsectNumber("爬满仓库");//害虫密度
vo.setInWater("11.3");//入库水分
vo.setItemName("小麦");//品种
vo.setNowWater("11.4");//现水分
vo.setNumber("6457.43吨");//数量
vo.setSource("山东");//来源
vo.setStoreHouserCode("CK001");//仓编号
vo.setStoreHumidity("88.2");//仓湿
vo.setStoreTemperature("18.1");//仓温
vo.setTemperature("15.2");//气温
vo.setUserName("小白");//用户名
vo.setWeather("晴");//天气
vo.setWind("东风");//风向
vo.setThermometerStr("21.1
*/@SuppressLint("WrongViewCast")
public class ChaCangRecordActivity extends Activity {
	EditText Humidity,InDate,Insect,InsectNumber,InWater,ItemName,NowWater,Number,Source,StoreHouserCode,
	StoreHumidity,StoreTemperature,Temperature,Weather,Wind;
	@SuppressLint("WrongViewCast")
	EditText[] edis ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chacang_record);
		edis = new EditText[]{
				(EditText)findViewById(R.id.a11),(EditText)findViewById(R.id.a12),(EditText)findViewById(R.id.a13),(EditText)findViewById(R.id.a14),
				(EditText)findViewById(R.id.b11),(EditText)findViewById(R.id.b12),(EditText)findViewById(R.id.b13),(EditText)findViewById(R.id.b14),
				(EditText)findViewById(R.id.c11),(EditText)findViewById(R.id.c12),(EditText)findViewById(R.id.c13),(EditText)findViewById(R.id.c14),
				
				(EditText)findViewById(R.id.a21),(EditText)findViewById(R.id.a22),(EditText)findViewById(R.id.a23),(EditText)findViewById(R.id.a24),
				(EditText)findViewById(R.id.b21),(EditText)findViewById(R.id.b22),(EditText)findViewById(R.id.b23),(EditText)findViewById(R.id.b24),
				(EditText)findViewById(R.id.c21),(EditText)findViewById(R.id.c22),(EditText)findViewById(R.id.c23),(EditText)findViewById(R.id.c24),
				
				(EditText)findViewById(R.id.a31),(EditText)findViewById(R.id.a32),(EditText)findViewById(R.id.a33),(EditText)findViewById(R.id.a34),
				(EditText)findViewById(R.id.b31),(EditText)findViewById(R.id.b32),(EditText)findViewById(R.id.b33),(EditText)findViewById(R.id.b34),
				(EditText)findViewById(R.id.c31),(EditText)findViewById(R.id.c32),(EditText)findViewById(R.id.c33),(EditText)findViewById(R.id.c34),
				
				(EditText)findViewById(R.id.a41),(EditText)findViewById(R.id.a42),(EditText)findViewById(R.id.a43),(EditText)findViewById(R.id.a44),
				(EditText)findViewById(R.id.b41),(EditText)findViewById(R.id.b42),(EditText)findViewById(R.id.b43),(EditText)findViewById(R.id.b44),
				(EditText)findViewById(R.id.c41),(EditText)findViewById(R.id.c42),(EditText)findViewById(R.id.c43),(EditText)findViewById(R.id.c44),
				
				(EditText)findViewById(R.id.a51),(EditText)findViewById(R.id.a52),(EditText)findViewById(R.id.a53),(EditText)findViewById(R.id.a54),
				(EditText)findViewById(R.id.b51),(EditText)findViewById(R.id.b52),(EditText)findViewById(R.id.b53),(EditText)findViewById(R.id.b54),
				(EditText)findViewById(R.id.c51),(EditText)findViewById(R.id.c52),(EditText)findViewById(R.id.c53),(EditText)findViewById(R.id.c54),
				
				(EditText)findViewById(R.id.a61),(EditText)findViewById(R.id.a62),(EditText)findViewById(R.id.a63),(EditText)findViewById(R.id.a64),
				(EditText)findViewById(R.id.b61),(EditText)findViewById(R.id.b62),(EditText)findViewById(R.id.b63),(EditText)findViewById(R.id.b64),
				(EditText)findViewById(R.id.c61),(EditText)findViewById(R.id.c62),(EditText)findViewById(R.id.c63),(EditText)findViewById(R.id.c64),
				
				(EditText)findViewById(R.id.a71),(EditText)findViewById(R.id.a72),(EditText)findViewById(R.id.a73),(EditText)findViewById(R.id.a74),
				(EditText)findViewById(R.id.b71),(EditText)findViewById(R.id.b72),(EditText)findViewById(R.id.b73),(EditText)findViewById(R.id.b74),
				(EditText)findViewById(R.id.c71),(EditText)findViewById(R.id.c72),(EditText)findViewById(R.id.c73),(EditText)findViewById(R.id.c74),
				
				(EditText)findViewById(R.id.a81),(EditText)findViewById(R.id.a82),(EditText)findViewById(R.id.a83),(EditText)findViewById(R.id.a84),
				(EditText)findViewById(R.id.b81),(EditText)findViewById(R.id.b82),(EditText)findViewById(R.id.b83),(EditText)findViewById(R.id.b84),
				(EditText)findViewById(R.id.c81),(EditText)findViewById(R.id.c82),(EditText)findViewById(R.id.c83),(EditText)findViewById(R.id.c84),
				
				};
		findViewById(R.id.page_a).setVisibility(View.VISIBLE);
		findViewById(R.id.page_b).setVisibility(View.GONE);
		findViewById(R.id.page_c).setVisibility(View.GONE);
		initEdit();
		findViewById(R.id.StoreHouserCode).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(ChaCangRecordActivity.this,ChooseNOactivity.class), 701);
			}
		});
	}

	String store_no;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode==701 && data!=null){
			store_no = data.getStringExtra("storeno");
			String store_name = data.getStringExtra("storename");
			StoreHouserCode.setText(store_name);
		}
	}
	private void initEdit() {
		/*EditText Humidity,InDate,Insect,InsectNumber,InWater,ItemName,NowWater,Number,Source,StoreHouserCode,
		StoreHumidity,StoreTemperature,Temperature,Weather,Wind;*/
		Humidity = (EditText) findViewById(R.id.Humidity);
		InDate = (EditText) findViewById(R.id.InDate);
		Insect = (EditText) findViewById(R.id.Insect);
		InsectNumber = (EditText) findViewById(R.id.InsectNumber);
		ItemName = (EditText) findViewById(R.id.ItemName);
		NowWater = (EditText) findViewById(R.id.NowWater);
		Number = (EditText) findViewById(R.id.Number);
		Source = (EditText) findViewById(R.id.Source);
		StoreHouserCode = (EditText) findViewById(R.id.StoreHouserCode);
		StoreHumidity = (EditText) findViewById(R.id.StoreHumidity);
		StoreTemperature = (EditText) findViewById(R.id.StoreTemperature);
		Temperature = (EditText) findViewById(R.id.Temperature);
		Weather = (EditText) findViewById(R.id.Weather);
		Wind = (EditText) findViewById(R.id.Wind);
		InWater = (EditText) findViewById(R.id.InWater);
	}

	/**提交
	 * @param view
	 */
	public void tijiao(View view){
		switch (view.getId()) {
		case R.id.tijiao:
			if(TextUtils.isEmpty(StoreHouserCode.getText().toString())){
				Toast.makeText(this, "仓号不能为空", Toast.LENGTH_LONG).show();return;
			}
			VO vo = new VO();
			vo.setHumidity(Humidity.getText().toString());//气湿
			vo.setInDate(InDate.getText().toString());//时间
			vo.setInsect(Insect.getText().toString());//虫种
			vo.setInsectNumber(InsectNumber.getText().toString());//害虫密度
			vo.setInWater(InWater.getText().toString());//入库水分
			vo.setItemName(ItemName.getText().toString());//品种
			vo.setNowWater(NowWater.getText().toString());//现水分
			vo.setNumber(Number.getText().toString());//数量
			vo.setSource(Source.getText().toString());//来源
			vo.setStoreHouserCode(store_no);//仓编号
			vo.setStoreHumidity(StoreHumidity.getText().toString());//仓湿
			vo.setStoreTemperature(StoreTemperature.getText().toString());//仓温
			vo.setTemperature(Temperature.getText().toString());//气温
			vo.setUserName(Constants.username);//用户名
			vo.setWeather(Weather.getText().toString());//天气
			vo.setWind(Wind.getText().toString());//风向
			String thermometerStr = "";
			StringBuilder sBuilder = new StringBuilder();
			for(int i=0;i<edis.length;i++){
				if(i!=0&&((i+1)%12==0)&&i!=(edis.length-1)){
					sBuilder.append(TextUtils.isEmpty(edis[i].getText().toString())?"0":edis[i].getText().toString()).append(";");
				}else{
					sBuilder.append(TextUtils.isEmpty(edis[i].getText().toString())?"0":edis[i].getText().toString()).append(",");
				}
			}
			thermometerStr = sBuilder.toString();
			thermometerStr = thermometerStr.substring(0, thermometerStr.length()-1);
			vo.setThermometerStr(thermometerStr);
			String jsonString = new Gson().toJson(vo);
			String url = getIntent().getStringExtra("webhead")+Constants.URL_ChaCang_record;
			RequestParams rParams = new RequestParams();
			rParams.put("bean", jsonString);
			final ProgressDialog pd = new ProgressDialog(this);
			pd.setMessage("正在提交...");
			pd.show();
			HtlClientUtils.getContent(url, rParams, false, new HtlClientCallBack() {
				
				@Override
				public void contentResponse(boolean isSuccess, int statusCode,
						Header[] headers, byte[] responseBody) {
					if(pd!=null) pd.dismiss();
					if(isSuccess){
						MsgJsonReturn mjr = new Gson().fromJson(new String(responseBody), MsgJsonReturn.class);
						if("3".equalsIgnoreCase(mjr.getReturnMsgMap().getReturnAjaxState())){
							showToast(mjr.getReturnMsgMap().getMsg());
						}else{
							showToast(mjr.getReturnMsgMap().getMsg());
						}
					}else{
						showToast("服务器返回异常,请重试");
					}
				}
			});
			
			break;

		default:
			break;
		}
	}
	public void showToast(String msg){
		Toast.makeText(ChaCangRecordActivity.this, msg, Toast.LENGTH_SHORT).show();
	}
	public void tableTitleclick(View view){
		switch (view.getId()) {
		case R.id.table_a:
			findViewById(R.id.page_a).setVisibility(View.VISIBLE);
			findViewById(R.id.page_b).setVisibility(View.GONE);
			findViewById(R.id.page_c).setVisibility(View.GONE);
			findViewById(R.id.titleline_a).setVisibility(View.VISIBLE);
			findViewById(R.id.titleline_b).setVisibility(View.GONE);
			findViewById(R.id.titleline_c).setVisibility(View.GONE);
			break;
		case R.id.table_b:
			findViewById(R.id.page_b).setVisibility(View.VISIBLE);
			findViewById(R.id.page_a).setVisibility(View.GONE);
			findViewById(R.id.page_c).setVisibility(View.GONE);
			findViewById(R.id.titleline_b).setVisibility(View.VISIBLE);
			findViewById(R.id.titleline_a).setVisibility(View.GONE);
			findViewById(R.id.titleline_c).setVisibility(View.GONE);
			break;
		case R.id.table_c:
			findViewById(R.id.page_c).setVisibility(View.VISIBLE);
			findViewById(R.id.page_b).setVisibility(View.GONE);
			findViewById(R.id.page_a).setVisibility(View.GONE);
			findViewById(R.id.titleline_c).setVisibility(View.VISIBLE);
			findViewById(R.id.titleline_b).setVisibility(View.GONE);
			findViewById(R.id.titleline_a).setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

}
