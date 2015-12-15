package com.enjoyor.soft.product.TongFeng.Utils;

import android.content.Context;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 83916 on 2015/12/7.
 */
public class EditChangeListener implements TextWatcher{
    private EditText et_num;
    private CharSequence temp; //监听前的文本
    private int editStart;  //光标开始位置
    private int editEnd;  //光标结束位置
    private  int maxNum = 2;   //最大输入数
    private String TAG = "EditChangeListener";
    private Context context;

    public EditChangeListener(EditText et_num, Context context) {
        this.et_num = et_num;
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i(TAG,"输入文本前的状态");
        temp =s;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i(TAG,"输入文字中的状态,count是一次性输入字符数");
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.i(TAG,"输入文字后的状态");
        /**
         * 得到光标开始和结束位置，超过最大数后记录刚超出的数字索引进行控制
         */

        editStart  = et_num.getSelectionStart();
        editEnd = et_num.getSelectionEnd();
        if (temp.length() > maxNum){
            Toast.makeText(context,"你输入的字数已经超过了限制",Toast.LENGTH_SHORT).show();
            s.delete(editStart-1,editEnd);
            int tempSelection = editEnd;
            et_num.setText(s);
            et_num.setSelection(tempSelection);

        }
    }
}
