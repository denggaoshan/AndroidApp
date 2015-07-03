package com.example.administrator.androidapp.msg;

import com.example.administrator.androidapp.tool.ToolClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/3.
 */
public class User {

    private String UserID= "";
    private String Account= "";
    private String Avatar= "";
    private String NickName= "";
    private String Sex= "";
    private String Age= "";
    private String Constellation= "";
    private String Profession= "";
    private String LivePlace= "";
    private String Description= "";
    private String Phone= "";
    private String Mailbox= "";
    private String IsCheckedMailbox= "";
    private String QQ= "";
    private String WeiBo= "";
    private String RoleID= "";
    private String RegisterTime= "";

    private String good="";

    public static User createUser(JSONObject jsonObject)
    {
        User temp = new User();
        String[] tmp = {"UserID","Account","Avatar","NickName","Sex","Age",
                "Constellation","Profession","LivePlace","Description","Phone",
                "Mailbox","IsCheckedMailbox","QQ","WeiBo","RoleID","RegisterTime"};

        if (jsonObject != null) {
            for(String val :tmp){
                temp.setProperty(val, jsonObject);
            }
        }

        return temp;
    }


    private void setGood(JSONObject jsonObject)
    {

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
