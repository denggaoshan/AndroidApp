package com.example.administrator.androidapp;


import android.widget.TextView;

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

    private String mess;
    private String UserID;
    private String Account;public String getAccount(){return Account;}
    private String Avatar;
    private String NickName;
    private String Sex;
    private String Age;
    private String Constellation;
    private String Profession;
    private String LivePlace;
    private String Description;
    private String Phone;
    private String Mailbox;
    private String IsCheckedMailbox;
    private String QQ;
    private String WeiBo;
    private String RoleID;
    private String RegisterTime;
    private Activity[] activities;

    public static void setUser(User us){
        currentUser = us;
    }
    public static User getCurrentUser(){
        return currentUser;
    }

    public boolean ifLoading(){
        if(mess.equals("loginfail")){
            return false;
        }
        return true;
    }

    public Activity[] getActivities(){return activities;}
    private String good;

    private String isgood;

    private static User currentUser;



    private void getProperty(String data,JSONObject userMsg){
        Field fs;
        String value;
        try
        {
            fs= this.getClass().getField(data);
            fs.setAccessible(true);
        }
        catch (NoSuchFieldException e)
        {
            return;
        }
        try
        {
            value = userMsg.getString(data);
        }
        catch (JSONException e)
        {
            value = null;
        }
        try {
            fs.set(this,data);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

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

    private void getActivitiesByJson(JSONObject allMsg) {
        JSONArray jsonArray = null;
        try {
            jsonArray = allMsg.getJSONArray("activities");
        }
        catch (JSONException E) {
            jsonArray = null;
        }

        if (jsonArray != null) {
            if (jsonArray.length() != 0) {
                activities = new Activity[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try
                    {
                        activities[i] = new Activity(jsonArray.getJSONObject(i));
                    }
                    catch (JSONException e)
                    {
                        activities[i] = null;
                    }
                }
            }
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
        String[] tmp = {"UserID","Account","Avatar","NickName","Sex","Age",
                "Constellation","Profession","LivePlace","Description","Phone",
                "Mailbox","IsCheckedMailbox","QQ","WeiBo","RoleID","RegisterTime"};
        JSONObject userMsg;

        try {
            userMsg = allMsg.getJSONObject("user");
        }
        catch (JSONException e) {
            userMsg = null;
        }

        if (userMsg != null) {
            for(String val :tmp){
                getProperty(val,userMsg);
            }
        }

    }

    /*
    public User(String jsonString, boolean nouse)
    {
        JSONObject allMsg;
        try {
            allMsg = new JSONObject(jsonString);
        }
        catch (JSONException e)
        {
            allMsg = null;
        }
        getProperty("mess", allMsg);

        String[] tmp = {"UserID","Account","Avatar","NickName","Sex","Age",
                "Constellation","Profession","LivePlace","Description","Phone",
                "Mailbox","IsCheckedMailbox","QQ","WeiBo","RoleID","RegisterTime"};
        JSONObject userMsg;
        try {
            userMsg = allMsg.getJSONObject("user");
        }
        catch (JSONException e) {
            userMsg = null;
        }
        if (userMsg != null) {
            for(String val :tmp){
                getProperty(val,userMsg);
            }
        }

        getProperty("good", allMsg);
        getProperty("isgood", allMsg);

        JSONArray jsonArray = null;
        try {
            jsonArray = allMsg.getJSONArray("activities");
        }
        catch (JSONException E) {
            jsonArray = null;
        }

        if (jsonArray != null) {
            if (jsonArray.length() != 0) {
                activities = new Activity[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    try
                    {
                        activities[i] = new Activity(jsonArray.getJSONObject(i));
                    }
                    catch (JSONException e)
                    {
                        activities[i] = null;
                    }
                }
            }
        }
    }
    */

    /*
    public User[] getUsers(String jsonString)
    {
        User [] result = null;
        try
        {
            JSONObject allMsg = new JSONObject(jsonString);
            mess = allMsg.getString("mess");

            JSONArray jsonArray = allMsg.getJSONArray("users");
            result = new User[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                result[i] = new User();
                String[] tmp = {"UserID","Account","Avatar","NickName","Sex","Age",
                        "Constellation","Profession","LivePlace","Description","Phone",
                        "Mailbox","IsCheckedMailbox","QQ","WeiBo","RoleID","RegisterTime"};
                for (String var : tmp)
                    result[i].getProperty(var, jsonArray.getJSONObject(i));
            }
        }
        catch (JSONException E)
        {

        }

        return result;
    }
    */



    public void loadInformationToTextEdit(TextView tv,String data) {
        try {
            Field  fs= this.getClass().getDeclaredField(data);
            fs.setAccessible(true);

            String val = (String)fs.get(this);
            tv.setText(val);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}