package com.enjoyor.soft.product.TongFeng;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.enjoyor.soft.R;
import com.enjoyor.soft.product.TongFeng.Adapter.CloseAdapter;
import com.enjoyor.soft.product.TongFeng.Adapter.MyAdapter;
import com.enjoyor.soft.product.TongFeng.Adapter.OpenAdapter;
import com.enjoyor.soft.product.TongFeng.Entity.TongFeng;
import com.enjoyor.soft.product.TongFeng.Utils.HttpUtil;
import com.enjoyor.soft.product.TongFeng.Utils.JsonUtil;
import com.enjoyor.soft.product.TongFeng.Utils.PreferenceService;
import com.lidroid.xutils.http.RequestParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TongFengActivity extends Activity implements View.OnClickListener, HttpUtil.GetJsonCallBack {
    private Spinner spinner;
    private ListView lv_Content;
    private List<TongFeng> tongFengList;
    private MyAdapter adapter;
    private Button open_all, close_all, btn_confrim, btn_cancel;
    private String[] Cangku = {"一号仓库", "二号仓库"};
    private ArrayAdapter arrayAdapter;
    private EditText tongfeng_ip1, tongfeng_ip2, tongfeng_ip3, tongfeng_ip4;
    private Button btn_Submit;
    private String TONGFENG_IP;
    private String TONGFENG_IP2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int cangkuNum = 0;    //仓库编号
    private PreferenceService preferenceService;
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_feng);
        init();
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TONGFENG_IP = "http://" + tongfeng_ip1.getText().toString() + "." + tongfeng_ip2.getText().toString() + "." + tongfeng_ip3.getText().toString() + "." + tongfeng_ip4.getText().toString() + ":7000/twenty.html";
                TONGFENG_IP2 = "http://" + tongfeng_ip1.getText().toString() + "." + tongfeng_ip2.getText().toString() + "." + tongfeng_ip3.getText().toString() + "." + tongfeng_ip4.getText().toString() + ":7000/twentyOne.html";
                try {
                    //保存上一次输入的IP地址
                    preferenceService.save(tongfeng_ip1.getText().toString(), tongfeng_ip2.getText().toString(), tongfeng_ip3.getText().toString(), tongfeng_ip4.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                HttpUtil.getJsonFromNet(getApplicationContext(), TONGFENG_IP, new HttpUtil.GetJsonCallBack() {
                    @Override
                    public void callback(String jsonStr) {
                        String a[] = jsonStr.split(">");
                        String b[] = a[1].split("<");
                        String json = b[0];
                        initData(json);
                    }
                });
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if ("一号仓库".equals(parent.getItemAtPosition(position).toString())) {
                            cangkuNum = position;
                            Log.i("123+++++++++++++++", position + "," + spinner.getSelectedItemPosition());
                            HttpUtil.getJsonFromNet(getApplicationContext(), TONGFENG_IP, new HttpUtil.GetJsonCallBack() {
                                @Override
                                public void callback(String jsonStr) {
                                    //解析数据
                                    String a[] = jsonStr.split(">");
                                    String b[] = a[1].split("<");
                                    String json = b[0];
                                    initData(json);
                                }
                            });
                        } else if ("二号仓库".equals(parent.getItemAtPosition(position).toString())) {
                            cangkuNum = position;
                            Log.i("123++++++++", position + "");
                            HttpUtil.getJsonFromNet(getApplicationContext(), TONGFENG_IP2, new HttpUtil.GetJsonCallBack() {
                                @Override
                                public void callback(String jsonStr) {
                                    //解析数据
                                    String a[] = jsonStr.split(">");
                                    String b[] = a[1].split("<");
                                    String json = b[0];
                                    initData(json);
                                }
                            });
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
        preferenceService = new PreferenceService(getApplicationContext());
        //取出存储的IP地址
        map = new HashMap<>();
        map = preferenceService.getPreferences();
        tongfeng_ip1.setText(map.get("ip1").toString());
        tongfeng_ip2.setText(map.get("ip2").toString());
        tongfeng_ip3.setText(map.get("ip3").toString());
        tongfeng_ip4.setText(map.get("ip4").toString());
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Cangku);
        spinner.setAdapter(arrayAdapter);
        //spinner.setSelection(0, true);


        //设置刷新时的动画的颜色
        swipeRefreshLayout.setProgressBackgroundColor(android.R.color.white);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));


        //下拉监听事件
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("test", "onrefresh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (cangkuNum == 0) {
                            HttpUtil.getJsonFromNet(getApplicationContext(), TONGFENG_IP, new HttpUtil.GetJsonCallBack() {
                                @Override
                                public void callback(String jsonStr) {
                                    String a[] = jsonStr.split(">");
                                    String b[] = a[1].split("<");
                                    String json = b[0];
                                    initData(json);
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        } else if (cangkuNum == 1) {
                            HttpUtil.getJsonFromNet(getApplicationContext(), TONGFENG_IP2, new HttpUtil.GetJsonCallBack() {
                                @Override
                                public void callback(String jsonStr) {
                                    String a[] = jsonStr.split(">");
                                    String b[] = a[1].split("<");
                                    String json = b[0];
                                    initData(json);
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    }
                }, 2000);
            }
        });
        //此处添加网络请求
        //HttpUtil.getJsonFromNet(getApplicationContext(), Constants.URL_TONGFENG_1, this);
        //Log.i("123+++++++++", "123");
        tongFengList = new ArrayList<>();
    }


    public void init() {
        lv_Content = (ListView) findViewById(R.id.lv_Content);
        open_all = (Button) findViewById(R.id.open_all);
        close_all = (Button) findViewById(R.id.close_all);
        btn_confrim = (Button) findViewById(R.id.btn_confrim);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        spinner = (Spinner) findViewById(R.id.spinner);
        tongfeng_ip1 = (EditText) findViewById(R.id.tongfeng_ip1);
        tongfeng_ip2 = (EditText) findViewById(R.id.tongfeng_ip2);
        tongfeng_ip3 = (EditText) findViewById(R.id.tongfeng_ip3);
        tongfeng_ip4 = (EditText) findViewById(R.id.tongfeng_ip4);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.Swiperefreshlayout);
        btn_Submit = (Button) findViewById(R.id.btn_submit);
        open_all.setOnClickListener(this);
        close_all.setOnClickListener(this);
        btn_confrim.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_all:
                //adapter.openAll();
                btn_confrim.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                btn_confrim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openAll();
                        //上传参数给服务器
                        if (cangkuNum == 0) {
                            RequestParams params = new RequestParams();
                            params.addBodyParameter("BarnNo", "CK020");
                            params.addBodyParameter("Status", "OPENALL");
                            HttpUtil.GetJsonFromNet(getApplicationContext(), "http://192.168.1.177:7000/OpenClose/" + "CK020"
                                    + "/OPENALL", params);
                        } else if (cangkuNum == 1) {
                            RequestParams params = new RequestParams();
                            params.addBodyParameter("BarnNo", "CK021");
                            params.addBodyParameter("Status", "OPENALL");
                            HttpUtil.GetJsonFromNet(getApplicationContext(), "http://192.168.1.177:7000/OpenClose/" + "CK021"
                                    + "/OPENALL", params);
                        }
                        btn_confrim.setVisibility(View.INVISIBLE);
                        btn_cancel.setVisibility(View.INVISIBLE);
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "操作已取消", Toast.LENGTH_SHORT).show();
                        btn_confrim.setVisibility(View.INVISIBLE);
                        btn_cancel.setVisibility(View.INVISIBLE);
                    }
                });

                break;
            case R.id.close_all:
                btn_confrim.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                btn_confrim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeAll();
                        //上传参数给服务器
                        if (cangkuNum == 0) {
                            RequestParams params = new RequestParams();
                            params.addBodyParameter("BarnNo", "CK020");
                            params.addBodyParameter("Status", "CLOSEALL");
                            HttpUtil.GetJsonFromNet(getApplicationContext(), "http://192.168.1.177:7000/OpenClose/" + "CK020"
                                    + "/CLOSEALL", params);
                        } else if (cangkuNum == 1) {
                            RequestParams params = new RequestParams();
                            params.addBodyParameter("BarnNo", "CK021");
                            params.addBodyParameter("Status", "CLOSEALL");
                            HttpUtil.GetJsonFromNet(getApplicationContext(), "http://192.168.1.177:7000/OpenClose/" + "CK0201"
                                    + "/CLOSEALL", params);
                        }
                        btn_confrim.setVisibility(View.INVISIBLE);
                        btn_cancel.setVisibility(View.INVISIBLE);

                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "操作已取消", Toast.LENGTH_SHORT).show();
                        btn_confrim.setVisibility(View.INVISIBLE);
                        btn_cancel.setVisibility(View.INVISIBLE);
                    }
                });
                break;
        }
    }

    public void openAll() {
        OpenAdapter openAdapter = new OpenAdapter(getApplicationContext(), tongFengList);
        lv_Content.setAdapter(openAdapter);
        openAdapter.notifyDataSetChanged();
    }

    public void closeAll() {
        CloseAdapter closeAdapter = new CloseAdapter(getApplicationContext(), tongFengList);
        lv_Content.setAdapter(closeAdapter);
        closeAdapter.notifyDataSetChanged();
    }

//    public void init_item() {
//        ib_swift = (ImageButton) linearLayout.findViewById(R.id.ib_swift);
//        ib_distance = (ImageButton) linearLayout.findViewById(R.id.ib_distance);
//    }


    @Override
    public void callback(final String jsonStr) {
        Log.i("123+++++++++", jsonStr);
        //解析数据
        String a[] = jsonStr.split(">");
        String b[] = a[1].split("<");
        String json = b[0];
        initData(json);
        Log.i("123+++++++++", json);
    }

    public void initData(String jsonStr) {
        tongFengList = JsonUtil.parseJson(jsonStr);
        Log.i("123+++++++++", tongFengList.toString());
        //创建适配器对象
        adapter = new MyAdapter(getApplicationContext(), tongFengList);
        lv_Content.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
