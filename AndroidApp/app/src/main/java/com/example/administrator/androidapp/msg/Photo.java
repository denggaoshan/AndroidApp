package com.example.administrator.androidapp.msg;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/7/3.
 */
public class Photo extends BaseType
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
        temp.loadAllPropertyFromJSON(jsonObject);
        return temp;
    }
}
