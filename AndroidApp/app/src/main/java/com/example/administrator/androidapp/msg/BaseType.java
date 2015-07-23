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

    /**
     * 从Json串中加载此对象的所有属性
     * @param json
     */
    public void loadAllPropertyFromJSON(JSONObject json){
            Field[] allProperty = this.getClass().getDeclaredFields();
            for(Field property:allProperty){
                try
                {
                    String name = property.getName();
                    String value = json.getString(name);
                    property.setAccessible(true);
                    property.set(this,value);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    try {
                        property.set(this,"");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
    }


    /**
     * 抽象工厂类 把一个json对象转换成为相应的类对象
     * @param source
     * @param json
     * @return
     */
    public static BaseType create(Class source,JSONObject json){
        BaseType ret = null;
        try {
           ret = (BaseType)source.newInstance();
           ret.loadAllPropertyFromJSON(json);
           return ret;
       } catch (Exception e) {
            e.printStackTrace();
           return ret;
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
