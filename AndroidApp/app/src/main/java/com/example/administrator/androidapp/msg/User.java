package com.example.administrator.androidapp.msg;

import android.widget.TextView;

import com.example.administrator.androidapp.msg.ToolClass;

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

    private String good= "";
    private String isgood = "";

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
        temp.setGood(jsonObject);
        temp.setIsgood(jsonObject);

        return temp;
    }

    public String getUserID()
    {
        return UserID;
    }

    public String getAccount()
    {
        return Account;
    }

    public void loadInformationToTextEdit(TextView tv,String data) {
        try {
            Field  fs= this.getClass().getDeclaredField(data);
            fs.setAccessible(true);

            String val = (String)fs.get(this);
            if(val != null || !val.equals("null")){
                tv.setText(val);
            }else{
                tv.setText("");
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setGood(JSONObject jsonObject)
    {
        try
        {
            good = jsonObject.getString("good");
        }
        catch (JSONException e) {
            good = "";
        }
    }

    private void setIsgood(JSONObject jsonObject)
    {
        try
        {
            isgood = jsonObject.getString("isgood");
        }
        catch (JSONException e) {
            isgood = "";
        }
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
