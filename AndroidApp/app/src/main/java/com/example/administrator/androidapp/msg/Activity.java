package com.example.administrator.androidapp.msg;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/3.
 */
public class Activity {

    private String ActivityID;
    private String UserID;
    private String Title;public String getTitle(){return Title;}
    private String Content;
    private String StartTime;public String getStartTime(){return StartTime;}
    private String EndTime;
    private String Place;public String getPlace(){return Place;}
    private String Type;
    private String UserCount;public String getUserCount(){return UserCount;}
    private String IsChecked;
    private String NickName;
    private String Avatar;

    public static Activity createActivity(JSONObject jsonObject)
    {
        Activity temp = new Activity();
        String[] tmp = {"ActivityID","UserID","Title","Content","StartTime","EndTime",
                "Place","Type","UserCount","IsChecked","NickName",
                "Avatar"};

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