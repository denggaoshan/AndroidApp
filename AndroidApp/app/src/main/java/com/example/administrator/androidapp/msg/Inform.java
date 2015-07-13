package com.example.administrator.androidapp.msg;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/6.
 */
public class Inform {
    private String InformID;
    private String TargetID;
    private String Form;
    private String Time;
    private String Type;
    private String Title;
    private String Content;
    private String IsRead;

    public static Inform createInform(JSONObject jsonObject)
    {
        Inform temp = new Inform();
        String[] tmp = {"InformID", "TargetID", "Form", "Time",
                "Type", "Title", "Content", "IsRead"};

        if (jsonObject != null) {
            for(String val :tmp){
                temp.setProperty(val, jsonObject);
            }
        }

        return temp;
    }

    private void setProperty(String data,JSONObject userMsg){
        Field fs = null;
        try
        {
            fs= this.getClass().getDeclaredField(data);
            fs.setAccessible(true);
            String value = userMsg.getString(data);
            fs.set(this,value);
        }
        catch (NoSuchFieldException e)//没找到对应属性
        {
            return;
        }
        catch (JSONException e)//没找到JSON
        {
            try {
                fs.set(this,"");
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        catch (IllegalAccessException e)//不允许设置属性
        {
            e.printStackTrace();
        }
    }
}