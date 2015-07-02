package com.example.administrator.androidapp;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/1.
 */
public class User {

    private String mess;
    private String UserID;
    private String Account;
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
    private String good;

    private String isgood;


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

    public User(String jsonString)
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

    public User()
    {

    }

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
}
