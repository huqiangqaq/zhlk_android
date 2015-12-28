package com.enjoyor.soft.system.index;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.enjoyor.soft.R;
import com.enjoyor.soft.common.Constants;
import com.enjoyor.soft.product.TongFeng.TongFengActivity;
import com.enjoyor.soft.product.car.activity.StoreTask;
import com.enjoyor.soft.product.car.service.StoreMsgService;
import com.enjoyor.soft.product.chacang.ChacangActivity;
import com.enjoyor.soft.product.megcenter.activity.MergeActivity;
import com.enjoyor.soft.product.nfcwirte.Activity_writerfid;
import com.enjoyor.soft.product.wenshidu.activity.Shidu;
import com.enjoyor.soft.product.wenshidu.activity.Wendu;
import com.enjoyor.soft.product.xungeng.ChaCangRecordActivity;
import com.enjoyor.soft.product.xungeng.XunGengActivity;

public class Menu extends Activity {
    AlertDialog.Builder ab;
    String webhead;
    int flag = 0;
    Intent ser;
    // 提交
    int item = -1;// 选择项
    GridView gridView;
    String username, password;
    String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu);

        Intent inte = getIntent();
        username = inte.getStringExtra("username");
        password = inte.getStringExtra("password");
        webhead = inte.getStringExtra("webhead");
        gridView = (GridView) findViewById(R.id.mGridView);
        // 生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        titles = new String[]{"温度管理", "湿度管理", "装卸货", "药品管理", "消息中心", "巡更",
                "查仓", "查仓记录", "RFID写入","手工通风控制"};
        String ts = getIntent().getStringExtra("moudles");
        if (ts != null && ts.contains(",")) {
            titles = ts.split(",");
        } else {
            titles = new String[]{ts};
        }
        // 如果包含消息中心模块则开启消息轮询功能
        if (ts != null && ts.contains("消息中心")) {
            startMsgCall();// 开启消息轮询
        }
        int[] ids = new int[titles.length];
        // 添加模块图标资源
        for (int i = 0; i < titles.length; i++) {
            if ("温度管理".equalsIgnoreCase(titles[i])) {
                ids[i] = R.drawable.wendu_icon;
            } else if ("湿度管理".equalsIgnoreCase(titles[i])) {
                ids[i] = R.drawable.shidu_icon;
            } else if ("装卸货".equalsIgnoreCase(titles[i])) {
                ids[i] = R.drawable.huo_icon;
            } else if ("药品管理".equalsIgnoreCase(titles[i])) {
                ids[i] = R.drawable.yao_icon;
            } else if ("消息中心".equalsIgnoreCase(titles[i])) {
                ids[i] = R.drawable.xiaoxi_icon;
            } else if ("巡更".equalsIgnoreCase(titles[i])) {
                ids[i] = R.drawable.xungeng_icon;
            } else if ("查仓".equalsIgnoreCase(titles[i])) {
                ids[i] = R.drawable.xungeng_icon;
            } else if ("查仓记录".equalsIgnoreCase(titles[i])) {
                ids[i] = R.drawable.xungeng_icon;
            } else if ("RFID写入".equalsIgnoreCase(titles[i])) {
                ids[i] = R.drawable.xungeng_icon;
            } else if ("手工通风控制".equalsIgnoreCase(titles[i])) {
                ids[i] = R.drawable.xungeng_icon;
            }
        }
        for (int i = 0; i < ids.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", ids[i]);// 添加图像资源的ID
            map.put("ItemText", titles[i]);// 按序号做ItemText
            lstImageItem.add(map);
        }
        // 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem,// 数据来源
                R.layout.grid_item,// night_item的XML实现

                // 动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},
                // ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.ItemImage, R.id.ItemText});
        // 添加并且显示
        gridView.setAdapter(saImageItems);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                if (titles[position].equalsIgnoreCase("温度管理")) {
                    // if (position == 0) {
                    // 温度
                    startActivity(new Intent(Menu.this, Wendu.class));
                } else if (titles[position].equalsIgnoreCase("湿度管理")) {
                    // 湿度
                    startActivity(new Intent(Menu.this, Shidu.class));
                } else if (titles[position].equalsIgnoreCase("装卸货")) {
                    // 管理员 任务列表||扫描界面
                    Intent intent = new Intent(Menu.this, StoreTask.class);
                    intent.putExtra("userName", username);
                    intent.putExtra("password", password);
                    startActivity(intent);

                } else if (titles[position].equalsIgnoreCase("药品管理")) {
                    // 药品管理
                    ComponentName cn = new ComponentName(
                            "com.example.tiaoxingscan",
                            "com.enjoyor.drugmanager.activity.XzChoice");
                    Intent intent = new Intent();
                    intent.putExtra("userName", username);
                    intent.putExtra("password", password);
                    intent.putExtra("webhead", Constants.getWebHead(Menu.this));
                    intent.setComponent(cn);
                    startActivity(intent);
                } else if (titles[position].equalsIgnoreCase("消息中心")) {
                    // 消息中心
                    Intent intent = new Intent(Menu.this, MergeActivity.class);
                    intent.putExtra("userName", username);
                    intent.putExtra("password", password);
                    intent.putExtra("webhead", Constants.getWebHead(Menu.this));
                    startActivity(intent);
                } else if (titles[position].equalsIgnoreCase("巡更")) {
                    // 巡更
                    Intent intent = new Intent(Menu.this, XunGengActivity.class);
                    intent.putExtra("userName", username);
                    intent.putExtra("password", password);
                    intent.putExtra("webhead", Constants.getWebHead(Menu.this));
                    startActivity(intent);
                    ;
                } else if (titles[position].equalsIgnoreCase("查仓")) {
                    // 查仓
                    Intent intent = new Intent(Menu.this, ChacangActivity.class);
                    intent.putExtra("userName", username);
                    intent.putExtra("password", password);
                    intent.putExtra("webhead", Constants.getWebHead(Menu.this));
                    startActivity(intent);
                    ;
                } else if (titles[position].equalsIgnoreCase("查仓记录")) {
                    // 查仓记录
                    Intent intent = new Intent(Menu.this,
                            ChaCangRecordActivity.class);
                    intent.putExtra("userName", username);
                    intent.putExtra("password", password);
                    intent.putExtra("webhead", Constants.getWebHead(Menu.this));
                    startActivity(intent);

                } else if (titles[position].equalsIgnoreCase("RFID写入")) {
                    startActivity(new Intent(Menu.this,
                            Activity_writerfid.class));
                } else if (titles[position].equalsIgnoreCase("手工通风控制")) {
                    startActivity(new Intent(Menu.this, TongFengActivity.class));
                }
            }
        });
        ab = new AlertDialog.Builder(Menu.this);
        ab.setTitle("选择类型").setSingleChoiceItems(
                new String[]{"温度管理", "湿度管理", "装卸货管理", "药品管理"}, -1,
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item = which;
                    }
                });
        ab.setNegativeButton("取消", null);
        ab.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (item == 0) {
                    // 温度
                    startActivity(new Intent(Menu.this, Wendu.class));
                } else if (item == 1) {
                    // 湿度
                    startActivity(new Intent(Menu.this, Shidu.class));
                } else if (item == 2) {
                    // 管理员 任务列表||扫描界面
                    Intent intent = new Intent(Menu.this, StoreTask.class);
                    intent.putExtra("userName", username);
                    intent.putExtra("password", password);
                    startActivity(intent);
                } else if (item == 3) {
                    // 药品管理
                    ComponentName cn = new ComponentName(
                            "com.example.tiaoxingscan",
                            "com.example.tiaoxingscan.XzChoice");
                    Intent intent = new Intent();
                    intent.putExtra("userName", username);
                    intent.putExtra("password", password);
                    intent.putExtra("webhead", Constants.WEBURIHEAD);
                    intent.setComponent(cn);
                    startActivity(intent);
                } else {
                    Toast.makeText(Menu.this, "请选择类别", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        // ab.create().show();
    }

    public void startMsgCall() {
        // 进入菜单界面就会启动轮询服务
        ser = new Intent(this, StoreMsgService.class);
        startService(ser);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // TODO Auto-generated method stub
        menu.add(1001, 100, 1, "断开轮询服务");
        menu.add(1002, 101, 2, "开启轮询服务");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case 100:
                stopService(ser);
                break;
            case 101:
                startMsgCall();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        AlertDialog.Builder ab = new AlertDialog.Builder(Menu.this);
        ab.setTitle("警告").setMessage("是否退出");
        ab.setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                // 清空数据库表0817-13:54
                /*
				 * CarService dbsService = new CarService(Menu.this); //清空表
				 * dbsService.clearList();
				 */
                // stopService(ser);//退出程序就断开轮询
                exit();
            }
        }).setNegativeButton("取消", null).create().show();
    }

    public void exit() {
        super.onBackPressed();
    }
	/*
	 * @Override protected void onResume() { // TODO Auto-generated method stub
	 * flag +=1; if(flag!=1){ ab.show(); } if(flag>100){ flag = 2; }
	 * super.onResume(); }
	 * 
	 * @Override public boolean onCreateOptionsMenu(android.view.Menu menu) { //
	 * TODO Auto-generated method stub menu.add(0, 1, 1, "选择操作"); return
	 * super.onCreateOptionsMenu(menu); }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // TODO
	 * Auto-generated method stub switch (item.getItemId()) { case 1: ab.show();
	 * break; default: break; } return super.onOptionsItemSelected(item); }
	 */
}
