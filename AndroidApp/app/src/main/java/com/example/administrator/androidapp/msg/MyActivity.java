package com.example.administrator.androidapp.msg;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/3.
 */
public class MyActivity {

    private String ActivityID;public String getActivityID(){return ActivityID;}
    private String UserID;
    private String Title;public String getTitle(){return Title;}
    private String Content;public String getContent(){return Content;}
    private String StartTime;public String getStartTime(){return StartTime;}
    private String EndTime;public String getEndTime(){return EndTime;}
    private String Place;public String getPlace(){return Place;}
    private String Type;public  String getType(){return Type;}
    private String UserCount;public String getUserCount(){return UserCount;}
    private String IsChecked;public String getIsChecked(){return IsChecked;}
    private String NickName;
    private String Avatar;public String getAvatar(){return Avatar;}

    private static MyActivity currentActivity;
    public static MyActivity getCurrentActivity(){
        return currentActivity;
    }
    public static void setCurrentActivity(MyActivity activity){
        currentActivity = activity;
    }

    public static MyActivity createActivity(JSONObject jsonObject)
    {
        MyActivity temp = new MyActivity();
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


    //从userMsg中装载同名的属性，注意 data跟userMsg中大小写必须一致 我干
    private void setProperty(String data,JSONObject userMsg){
        Field fs = null;
        try
        {
            fs= this.getClass().getDeclaredField(data);
            fs.setAccessible(true);

            String value= null;
            try {
                value = userMsg.getString(data);
            }catch (Exception e){
                //如果第一个字母大小写不一致 尝试改下
                String tmp = Character.toLowerCase(data.charAt(0)) + data.substring(1,data.length());
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
}
