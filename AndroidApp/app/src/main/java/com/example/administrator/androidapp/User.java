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

    public User()
    {

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
                result[i].UserID = jsonArray.getJSONObject(i).getString("UserID");
                result[i].Account = jsonArray.getJSONObject(i).getString("Account");
                result[i].Avatar = jsonArray.getJSONObject(i).getString("Avatar");
                result[i].NickName = jsonArray.getJSONObject(i).getString("NickName");
                result[i].Sex = jsonArray.getJSONObject(i).getString("Sex");
                result[i].Age = jsonArray.getJSONObject(i).getString("Age");
                result[i].Constellation = jsonArray.getJSONObject(i).getString("Constellation");
                result[i].Profession = jsonArray.getJSONObject(i).getString("Profession");
                result[i].LivePlace = jsonArray.getJSONObject(i).getString("LivePlace");
                result[i].Description = jsonArray.getJSONObject(i).getString("Description");
                result[i].Phone = jsonArray.getJSONObject(i).getString("Phone");
                result[i].Mailbox = jsonArray.getJSONObject(i).getString("Mailbox");
                result[i].IsCheckedMailbox = jsonArray.getJSONObject(i).getString("IsCheckedMailbox");
                result[i].QQ = jsonArray.getJSONObject(i).getString("QQ");
                result[i].WeiBo = jsonArray.getJSONObject(i).getString("WeiBo");
                result[i].RoleID = jsonArray.getJSONObject(i).getString("RoleID");
                result[i].RegisterTime = jsonArray.getJSONObject(i).getString("RegisterTime");
            }
        }
        catch (JSONException E)
        {

        }

        return result;
    }
}
