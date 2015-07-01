package com.example.administrator.androidapp;

import org.json.JSONObject;

import android.app.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private void getProperty(String data){
        this.getClass();

    }

    public User(String jsonString)
    {
        try
        {
            JSONObject allMsg = new JSONObject(jsonString);
            mess = allMsg.getString("mess");

            JSONObject userMsg = allMsg.getJSONObject("user");
            UserID = userMsg.getString("UserID");
            Account = userMsg.getString("Account");
            Avatar = userMsg.getString("Avatar");
            NickName = userMsg.getString("NickName");
            Sex = userMsg.getString("Sex");
            Age = userMsg.getString("Age");
            Constellation = userMsg.getString("Constellation");
            Profession = userMsg.getString("Profession");
            LivePlace = userMsg.getString("LivePlace");
            Description = userMsg.getString("Description");
            Phone = userMsg.getString("Phone");
            Mailbox = userMsg.getString("Mailbox");
            IsCheckedMailbox = userMsg.getString("IsCheckedMailbox");
            QQ = userMsg.getString("QQ");
            WeiBo = userMsg.getString("WeiBo");
            RoleID = userMsg.getString("RoleID");
            RegisterTime = userMsg.getString("RegisterTime");

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
