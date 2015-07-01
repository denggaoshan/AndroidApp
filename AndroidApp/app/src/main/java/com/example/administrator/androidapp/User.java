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



    private void getProperty(String data,JSONObject userMsg){
        try {
            Field  fs= this.getClass().getField(data);
            fs.setAccessible(true);

            String val = userMsg.getString(data);
            fs.set(this,data);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public User(String jsonString)
    {
        try
        {
            JSONObject allMsg = new JSONObject(jsonString);
            mess = allMsg.getString("mess");

            String[] tmp = {"UserID","Account","Avatar","NickName","Sex","Age",
                    "Constellation","Profession","LivePlace","Description","Phone",
                    "Mailbox","IsCheckedMailbox","QQ","WeiBo","RoleID","RegisterTime"};

            JSONObject userMsg = allMsg.getJSONObject("user");

            for(String val :tmp){
                getProperty(val,userMsg);
            }

            good = allMsg.getString("good");


            JSONArray jsonArray = allMsg.getJSONArray("activities");
            if (jsonArray.length() != 0)
            {
                activities = new Activity[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    activities[i] = new Activity(jsonArray.getJSONObject(i));
                }
            }
        }
        catch (JSONException e)
        {

        }

    }
}
