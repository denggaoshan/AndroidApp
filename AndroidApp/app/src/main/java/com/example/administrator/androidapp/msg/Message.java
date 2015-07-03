package com.example.administrator.androidapp.msg;

import com.example.administrator.androidapp.msg.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/7/3.
 */
public class Message {

    private static String mess;
    private static User user;
    private static User[] users;
    private static Activity activity;
    private static Activity[] activities;

    public static Message createMessage(String jsonString, boolean moreUser, boolean moreActivity)
    {
        Message temp = new Message();

        JSONObject allMsg = temp.getAllMsg(jsonString);
        temp.setMess(allMsg);

        if (moreUser)
            temp.setUsers(allMsg);
        else
            temp.setUser(allMsg);

        if (moreActivity)
            temp.setActivities(allMsg);
        else
            temp.setActivity(allMsg);

        return temp;
    }

    private JSONObject getAllMsg(String jsonString)
    {
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

    private void setMess(JSONObject jsonObject)
    {
        try
        {
            mess = jsonObject.getString("mess");
        }
        catch (JSONException e)
        {
            mess = "";
        }
    }

    public void setUser(JSONObject jsonObject)
    {
        try {
            user = User.createUser(jsonObject.getJSONObject("user"));
        }
        catch (JSONException e)
        {
            user = null;
        }
    }

    public void setUsers(JSONObject jsonObject)
    {
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("users");
        }
        catch (JSONException E)
        {
            jsonArray = null;
        }

        if (jsonArray != null && jsonArray.length() != 0)
        {
            users = new User[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                try
                {
                    users[i] = User.createUser(jsonArray.getJSONObject(i));
                }
                catch (JSONException E)
                {
                    users[i] = null;
                }

            }
        }
        else
        {
            users = null;
        }
    }

    public void setActivity(JSONObject jsonObject)
    {
        try {
            activity = Activity.createActivity(jsonObject.getJSONObject("activity"));
        }
        catch (JSONException e)
        {
            activity = null;
        }
    }

    public void setActivities(JSONObject jsonObject)
    {
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("activities");
        }
        catch (JSONException E)
        {
            jsonArray = null;
        }

        if (jsonArray != null && jsonArray.length() != 0)
        {
            activities = new Activity[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                try
                {
                    activities[i] = Activity.createActivity(jsonArray.getJSONObject(i));
                }
                catch (JSONException E)
                {
                    activities[i] = null;
                }

            }
        }
        else
        {
            activities = null;
        }
    }


    public static String getMess() { return mess; }
    public static Activity getActivity() { return activity; }
    public static Activity[] getActivities() { return activities; }
    public static User getUser() { return user; }
    public static User[] getUsers() { return users; }

}
