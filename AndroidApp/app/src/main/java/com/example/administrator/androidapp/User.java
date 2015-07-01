package com.example.administrator.androidapp;

<<<<<<< HEAD
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
            activities = new Activity[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                activities[i] = new Activity(jsonArray.getJSONObject(i));
            }
        }
        catch (JSONException e)
        {

        }

    }
}
=======
import java.util.Map;

/**
 * Created by Administrator on 2015/7/1.
 */
public class User {

    public User(){
    }

    //用户的个人资料
    private String userId;
    private String account;
    private String avator;
    private String nickName;
    private String sex;
    private String age;
    private String constellation;
    private String profession;
    private String livePlace;
    private String description;
    private String phone;
    private String mailBox;
    private String isCheckedMailbox;
    private String qqNumber;
    private String weiBo;
    private String roleID;
    private String registerTime;


    //用户有关的活动
    private String activityID;

    public User(Map<String, Object> data){
        Map<String,String> result = (Map<String,String> )data.get("user");

    }
}
>>>>>>> origin/develop
