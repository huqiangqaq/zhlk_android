package com.enjoyor.soft.product.cyweight;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.enjoyor.soft.R;

public class CYWeight extends Activity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup rb_tab_bar;
    private RadioButton rb_tab_btn1,rb_tab_btn2;
    private Button btn_detail;
    private Button btn_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyweight);
        init();
        rb_tab_bar.setOnCheckedChangeListener(this);
    }

    private void init() {
        rb_tab_bar = (RadioGroup) findViewById(R.id.rb_tab_bar);
        btn_detail = (Button) findViewById(R.id.btn_detail);
        btn_end = (Button) findViewById(R.id.btn_end);
        rb_tab_btn1 = (RadioButton) findViewById(R.id.rb_tab_btn1);
        rb_tab_btn2 = (RadioButton) findViewById(R.id.rb_tab_btn2);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_tab_btn1:
                break;
            case R.id.rb_tab_btn2:
                break;
        }
    }
}
