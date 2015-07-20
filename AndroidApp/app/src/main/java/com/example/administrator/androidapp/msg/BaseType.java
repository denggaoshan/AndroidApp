package com.example.administrator.androidapp.msg;

import com.example.administrator.androidapp.tool.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/7/20.
 * 消息内容的基类
 */
public abstract class BaseType {

    public void loadAllPropertyFromJSON(JSONObject json){
            Field[] allProperty = this.getClass().getDeclaredFields();
            for(Field property:allProperty){
                try
                {
                    String name = property.getName();
                    String value = json.getString(name);

                    property.setAccessible(true);
                    property.set(this,value);
                }catch (JSONException e){
                    try {
                        //
                        property.set(this,"");
                    } catch (IllegalAccessException e1) {
                    }
                } catch (IllegalAccessException e) {
                    //权限不够，不存在
                }
            }
    }

    //从userMsg中装载同名的属性，注意 data跟userMsg中大小写必须一致 我干
    // 别乱改这个函数，否则整个系统就他妈崩了
    public void setProperty(String data,JSONObject userMsg){
        Field fs = null;
        try
        {
            fs= this.getClass().getDeclaredField(data);
            fs.setAccessible(true);
            String value= null;

            try {
                value = userMsg.getString(data);
            }catch (Exception e){
                String tmp = data.toLowerCase();
                try {
                    value = userMsg.getString(tmp);
                } catch (JSONException e1) {

                }
            }
            fs.set(this, value);
        }
        catch (NoSuchFieldException e)//没找到对应属性
        {
            return;
        }
        catch (IllegalAccessException e)//不允许设置属性
        {
            e.printStackTrace();
        }
    }

    //获得用户属性fieldName的值
    public String getFieldContent(String fieldName) {
        String ret = null;
        try {
            Field  fs= this.getClass().getDeclaredField(fieldName);
            fs.setAccessible(true);
            ret = (String)fs.get(this);
            if(ret != null && !ret.equals("null")){
                //转换性别
                if(fieldName.equals("Sex")){
                    ret = Utils.changeSex(ret);
                }
            }else{
                ret = "";
            }
        } catch (NoSuchFieldException e) {
            return "";
        }  catch (IllegalAccessException e) {
            return  "";
        }
        return ret;
    }
}
