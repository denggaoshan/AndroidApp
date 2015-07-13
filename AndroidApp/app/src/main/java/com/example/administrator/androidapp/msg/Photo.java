package com.example.administrator.androidapp.msg;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/7/3.
 */
public class Photo
{
    private String PhotoID;
    private String UserID;
    private String ActivityID;
    private String Address; public String getAddress(){return Address;}
    private String Title;
    private String Describe;
    private String Level;
    private String Time; public String getTime(){return Time;}
    private String NickName; public String getNickName(){return NickName;}
    private String Avatar; public String getAvatar(){return Avatar;}

    public static Photo createPhoto(JSONObject jsonObject)
    {
        Photo temp = new Photo();
        String[] tmp = {"PhotoID", "UserID", "ActivityID", "Address",
                "Title", "Describe", "Level", "Time", "NickName", "Avatar"};

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
