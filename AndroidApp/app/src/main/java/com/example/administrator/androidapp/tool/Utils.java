package com.example.administrator.androidapp.tool;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.page.*;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/7/3.
 */
public class Utils {

    //在parent中提取字段为key的子JSON
    public static JSONObject getJSONObject(JSONObject parent,String key){
        JSONObject ret;
        try {
            ret = parent.getJSONObject("user");
        }
        catch (JSONException e) {
            ret = null;
        }
        return ret;
    }

    //切换页面
    public static void transPage(ActionBarActivity before,Class after){
        Intent intent = new Intent();
        intent.setClass(before,after);
        before.startActivity(intent);
        before.finish();
    }

    //弹出提示窗口
    public static void showMessage(ActionBarActivity parent,String message){
        Toast.makeText(parent,message, Toast.LENGTH_LONG).show();
    }

    //返回文本框的内容
    public static String getSpinnerById(ActionBarActivity parent,int id){
        return ((Spinner) parent.findViewById(id)).getSelectedItem().toString();
    }

    //返回文本框的内容
    public static String getEditTextById(ActionBarActivity parent,int id){
       return ((EditText) parent.findViewById(id)).getText().toString();
    }
<<<<<<< HEAD
=======

    /******************* 适配器接口 *******************/
    //性别
    public static String changeSex(String val){
        if(val.equals("0")){
            return "男";
        }
        if(val.equals("1")){
            return "女";
        }
        return "出错";
    }
    //活动类型
    public static String changeType(String val){
        //0户外 1运动 2玩乐 3旅行 4音乐 5其他
        String[] data = {"户外","运动","玩乐","旅行","音乐","其他"};
        int index = Integer.parseInt(val);
        return data[index];
    }
>>>>>>> add
}
