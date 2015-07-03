package com.example.administrator.androidapp.core;


import android.widget.TextView;

import com.example.administrator.androidapp.tool.ToolClass;
import com.example.administrator.androidapp.tool.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/1.
 */
public class User {

    //禁止直接创建User实例
    private User(){};

    //登陆状态
    private String mess = "";

    //用户的个人信息
    private String UserID= "";public String getUserId(){return UserID;}
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

    //点赞数目
    private String good;
    private String isgood;

    //活动
    private Activity[] activities;

    //当前登陆的用户
    private static User currentUser;
    public String getAccount(){return Account;}
    public static User getCurrentUser(){return currentUser;}
    public Activity[] getActivities(){return activities;}
    public boolean ifLoading(){
        if(mess.equals("loginfail")){
            return false;
        }
        return true;
    }
    public static void setUser(User us){currentUser = us;}
    private void getProperty(String data,JSONObject userMsg){
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
    //获得所有的
    private JSONObject getAllMsg(String jsonString){
        JSONObject allMsg;
        try {
            allMsg = new JSONObject(jsonString);
        }
        catch (JSONException e)
        {
            allMsg = null;
        }
        return allMsg;
    }

    //通过Json串创建一个User对象
    public static User createUserByJson(String jsonString){
        User ret = new User();
        //获得返回的JSON
        JSONObject allMsg = ret.getAllMsg(jsonString);

        //读取登陆状态
        try {
            ret.mess = allMsg.getString("mess");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //读取用户信息
        ret.getUserInformationByJson(allMsg);
        //获得魅力度
        ret.getGoodInformationByJson(allMsg);
        //获得所有活动
        ret.getActivitiesByJson(allMsg);
        return ret;
    }
    //读取所有的活动信息
    private void getActivitiesByJson(JSONObject allMsg) {
        JSONArray jsonArray = null;
        try {
            jsonArray = allMsg.getJSONArray("activities");

            if (jsonArray == null || jsonArray.length() == 0) {
                return;
            }else{
                activities = new Activity[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try{
                       activities[i] = new Activity(jsonArray.getJSONObject(i));
                    }
                    catch (JSONException e){
                        activities[i] = null;
                    }
                }
            }
        }
        catch (JSONException E) {
            jsonArray = null;
        }
    }
    private void getGoodInformationByJson(JSONObject allMsg) {
        try {
            this.good = allMsg.getString("good");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //读取userMsg中的用户信息
    private void getUserInformationByJson(JSONObject allMsg) {
        JSONObject userMsg;
        String[] tmp = {"UserID","Account","Avatar","NickName","Sex","Age",
                "Constellation","Profession","LivePlace","Description","Phone",
                "Mailbox","IsCheckedMailbox","QQ","WeiBo","RoleID","RegisterTime"};

        userMsg = Utils.getJSONObject(allMsg, "user");

        if (userMsg != null) {
            for(String val :tmp){
                this.getProperty(val, userMsg);
            }
        }

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
}