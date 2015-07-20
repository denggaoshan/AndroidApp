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
                        property.set(this,"");
                    } catch (IllegalAccessException e1) {
                    }
                } catch (IllegalAccessException e) {
                    //权限不够，不存在
                }
            }
    }

    public static BaseType create(Class source,JSONObject json){
       try {
           BaseType ret = (BaseType)source.newInstance();
           ret.loadAllPropertyFromJSON(json);
           return ret;
       } catch (Exception e) {
       }
      return null;
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
